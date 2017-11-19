$(document).ready(function(){

    $('#middle-header-space').on('input', '#search_bar', function () {
        var fullName = $('#search_bar').val();

        if(fullName){

            var url = "http://localhost:8080/fakebook/users/search/" + fullName;

            $.ajax({
                url: url,
                dataType: 'json',
                type: 'GET',
                error: function() {
                    console.log("error: " + url);
                },

                success: function(data) {
                    var jsonString = JSON.stringify(data);
                    var userArray = JSON.parse(jsonString);
                    console.log("success: " + url);
                    console.log("\t" + jsonString);

                    // remove dropdown list on new list returned
                    if($("#search_bar_dropdown_list").length){
                        $("#search_bar_dropdown_list").remove();
                    }
                    // add dropdown (again)
                    $.get("html/search-bar-dropdown-list.html", function (data) {
                        $("#search_div").append(data);
                        return false;
                    });
                    console.log("added search bar dropdown");

                    // TODO: finish

                    userArray.map( function(item) {
                        var userString = JSON.stringify(item);
                        var user = JSON.parse(userString);

                        // append search result html to list

                        $.get("html/search-result.html", function (data) {

                            var wrapper= document.createElement('div');
                            wrapper.innerHTML = data;
                            var searchResultHtml = wrapper.firstChild;

                            // replace photo
                            // searchResultHtml.innerHTML = searchResultHtml.innerHTML.replace('src="png/profile-photo.png"', "something");
                            // replace text
                            searchResultHtml.innerHTML = searchResultHtml.innerHTML.replace("fname lname", user.firstName + ' ' + user.lastName);
                            $(searchResultHtml).on('click', function () {

                                console.log("go to profile of: " + user.id);
                                loadOtherProfileSidebar(user);

                                loadHomeButton(user);
                                hidePreviousSearch();
                            });

                         // TODO: add user ids to tags for easier look up upon click
                         //    https://www.w3schools.com/tags/att_data-.asp

                            $("#search_bar_dropdown_list").append(searchResultHtml);
                            console.log("\tappended search result to dropdown. " + user.firstName + " " + user.lastName);
                            return false;
                        });
                        return false;
                    });
                }
            });
        }
        else{
            if($("#search_bar_dropdown_list").length){
                $("#search_bar_dropdown_list").remove();
            }
        }
    });

    //TODO: figure out why it isnt working
    isLoggedIn();


});


function loginSuccess(userJson){
        var url = 'http://localhost:8080/fakebook/login-success';
        $.ajax({
            url: url,
            data: userJson,
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            error: function(data) {
                console.log("error: " + url);
            },
            success: function(data) {
                if(data === true){
                    console.log("success: " + url);
                    settingsMenuLoadOnLogin();
                    loadSearchBar();
                    loadProfileSidebar(userJson);
                    // TODO: this is commented out in dropdown-menu.js
                    // hideSettingsDropdown();
                    loadFeed();
                    loadAddPostToFeed(JSON.parse(userJson));
                    loadPostsToFeed(JSON.parse(userJson));
                }
                else{
                    console.log("error in controller: " + url);
                }
            }
        });
    return false;
}


function User (id, firstName, lastName, email, password, accountCreated) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.accountCreated = accountCreated;
}

function Post(postId, user, userPostedTo, postText, photo, postCreated){
    this.postId = postId;
    this.user = user;
    this.userPostedTo = userPostedTo;
    this.postText = postText;
    this.photo = photo;
    this.postCreated = postCreated;
}

function Comment(commentId, post, user, commentText, commentCreated){
    this.commentId = commentId;
    this.post = post;
    this.user = user;
    this.commentText = commentText;
    this.commentCreated = commentCreated

}

function hidePreviousSearch(){
    $("#search_bar_dropdown_list").remove();
    $("#search_bar").val('');
}

function loadFakebookHeader(){
    $.get("html/fakebook-header.html", function (data) {

        var wrapper= document.createElement('div');
        wrapper.innerHTML = data;
        var fakebookHeader = wrapper.firstChild;

        $("#left-header-space").empty();
        $("#left-header-space").append(fakebookHeader);
        console.log("added fakebook header")
    });
}

function loadFeed(){
    $.get("html/feed.html", function (data) {
        var wrapper = document.createElement('div');
        wrapper.innerHTML = data;
        var feedDiv = wrapper.firstChild;
        $("#middle-body-space").empty();
        $("#middle-body-space").append(feedDiv);
        console.log("added feed");
    });
}

function loadAddPostToFeed(user){
    var addPostDiv;
    $.get("html/add-post.html", function (data) {
        var wrapper = document.createElement('div');
        wrapper.innerHTML = data;
        addPostDiv = wrapper.firstChild;

        var jQaddPostDiv = $(addPostDiv);
        $(jQaddPostDiv).find('input.add-post-btn').on('click', function () {
            var text = $(jQaddPostDiv).find('textarea.add-post-text-area').val();
            var post = new Post(null, user, user, text, null, new Date().getTime());
            var url = 'http://localhost:8080/fakebook/posts/add';
            $.ajax({
                url: url,
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(post),
                type: 'POST',
                error: function() {
                    console.log("error: " + url);
                },

                success: function(data) {
                    console.log("success: " + url);
                    loadProfileSidebar(JSON.stringify(user));


                    return false;
                }
            });
        });

        $("#feed").append(addPostDiv);
    });
    console.log("added add post to feed");
}

function loadPostsToFeed(user) {
    console.log('loadPostsToFeed()');

    var url = "http://localhost:8080/fakebook/posts/feed/" + user.id;

    $.ajax({
        url: url,
        dataType: 'json',
        type: 'GET',
        error: function() {
            console.log("error: " + url);
        },

        success: function(data) {
            var jsonString = JSON.stringify(data);
            var postArray = JSON.parse(jsonString);
            console.log("success: " + url);

            var postHolderDiv = document.createElement('div');
            postHolderDiv.id = 'post_holder_div';

            $.each(postArray, function(index, value) {
                var postString = JSON.stringify(value);
                var post = JSON.parse(postString);
                // TODO: convert DOM node to jQuery object so find() method will work below
                // var newPostDiv = $(postDiv);// dom object > jquery object;

                $.get("html/post.html", function (data) {
                    var wrapper = document.createElement('div');
                    wrapper.innerHTML = data;
                    var postDiv = wrapper.firstChild; // dom object
                    var newPostDiv = $(postDiv); // jquery object
                    var d = new Date(post.postCreated);
                    var formatted = d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds()+ ' ' +  d.getDate() + '/' + (d.getMonth() + 1) + '/' + d.getFullYear();

                    $(newPostDiv).find('label.poster-label').text(post.user.firstName + ' ' + post.user.lastName);

                    // TODO: make sure users are returned with post data
                    if(post.user.id === post.userPostedTo.id){
                        $(newPostDiv).find('span.post-sent-to-arrow').remove();
                        $(newPostDiv).find('label.post-sent-to-label').remove();
                    }
                    else{
                        $(newPostDiv).find('label.post-sent-to-label').text(post.userPostedTo.firstName + ' ' + post.userPostedTo.lastName);
                    }

                    $(newPostDiv).find('.post-time').text(formatted);
                    $(newPostDiv).find('div.post-text-area').text(post.postText);
                    $(newPostDiv).find('span.like-count').text(post.likes.length);

                    $.each(post.comments, function (index, value2) {
                        $.get("html/comment.html", function (data) {
                            var wrapper = document.createElement('div');
                            wrapper.innerHTML = data;
                            var commentLi = wrapper.firstChild;
                            var newCommentLi = $(commentLi);
                            // not performing anything that needs jquery on this, so can stay as dom object
                            $(newCommentLi).find('div.post-comment-name').text(value2.user.firstName + ' ' + value2.user.lastName);
                            $(newCommentLi).find('div.post-comment-text').text(value2.commentText);
                            if(index === post.comments.length - 1){
                                $(newCommentLi).find('br.comment-br').remove();

                            }
                            $(newPostDiv).find('ul.comments-section').append(commentLi);
                        });
                    });

                    var addCommentDiv = $(newPostDiv).find('div.add-comment-div');

                    $(addCommentDiv).find('input.add-comment-btn').on('click', function () {
                        var text = $(addCommentDiv).find('textarea.add-comment-text-area').val();


                        // TODO: implement on other add comment btn in other-profile-loader.js
                        var comment = new Comment(null, null, null, text, new Date().getTime());
                        var url = "http://localhost:8080/fakebook/comments/" + value.postId;

                        $.ajax({
                            url: url,
                            contentType: 'application/json; charset=utf-8',
                            data: JSON.stringify(comment),
                            type: 'POST',
                            error: function() {
                                console.log("error: " + url);
                            },

                            success: function(data) {
                                console.log("success: " + url)
                            }
                        });

                        isLoggedIn();
                    });
                    $('#feed').append(newPostDiv);
                });
            });
            return false;
        }
    });
}
function isLoggedIn(){
    var url = "http://localhost:8080/fakebook/users/loggedin";

    $.ajax({
        url: url,
        dataType: 'json',
        type: 'GET',
        error: function() {
            console.log("error: " + url);
        },

        success: function(data) {
            var jsonString = JSON.stringify(data);
            var user = JSON.parse(jsonString);
            console.log("success: " + url);
            console.log("\t" + jsonString);

            if(user !== null){
                settingsMenuLoadOnLogin();
                loadSearchBar();
                loadProfileSidebar(jsonString);
                // TODO: this is commented out in dropdown-menu.js
                // hideSettingsDropdown();
                loadFeed();
                loadFakebookHeader();
                loadAddPostToFeed(user);
                loadPostsToFeed(user);
            }
            return false;
        }
    });
}