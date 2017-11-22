function loadOtherProfileSidebar(user) {
    $.get("html/other-profile-sidebar.html", function (data) {

        var wrapper= document.createElement('div');
        wrapper.innerHTML = data;
        var otherProfileSidebarContainer = wrapper.firstChild;
        otherProfileSidebarContainer.innerHTML =
            otherProfileSidebarContainer.innerHTML.replace("fn4me ln4me", user.firstName + " " + user.lastName);


        $('#left-body-space').empty();
        $('#left-body-space').append(otherProfileSidebarContainer);
        console.log("other profile sidebar loaded");
    });

    var url = "http://localhost:8080/fakebook/friends/arefriends/" + user.id;
    $.ajax({
        url: url,
        dataType: 'json',
        type: 'GET',
        error: function () {
            console.log("error: " + url);
        },
        success: function (data) {
            console.log("success: " + url);
            if(data === true){
                loadWall();
                loadAddPostToWall(user);
                loadPostsToWall(user);
            }
            else{
                loadAddFriendBtn(user);

                var url2 = "http://localhost:8080/fakebook/friends/requestsent/" + user.id;
                $.ajax({
                    url: url2,
                    dataType: 'text',
                    type: 'GET',
                    error: function () {
                        console.log("error: " + url2);
                    },
                    success: function (data) {
                        console.log("success: " + url2);

                        if(data === "You sent request"){
                            $("#add_friend_btn").text("Friend Request Sent");
                            $("#add_friend_btn").attr('disabled', true);
                        }
                        else if(data === "They sent request"){
                            $("#add_friend_btn").text("Friend Request Received");
                            $("#add_friend_btn").attr('disabled', true);
                        }
                    }
                });


                $("#middle-body-space").empty();
                $("#middle-body-space").html("<h1>Must be friends<br>to view Wall</h1>");
            }
        }
    });
}

function loadHomeButton(user){
    $.get("html/home-button.html", function (data) {

        var wrapper= document.createElement('div');
        wrapper.innerHTML = data;
        var homeButton = wrapper.firstChild;

        $(homeButton).on('click', function () {
            isLoggedIn();
        });

        $("#left-header-space").empty();
        $("#left-header-space").append(homeButton);
        console.log("added home button");
    });
}

function loadAddFriendBtn(user){
    $.get("html/add-friend-btn.html", function (data) {

        var wrapper= document.createElement('div');
        wrapper.innerHTML = data;
        var addFriendBtn = wrapper.firstChild;


        $(addFriendBtn).on('click', function () {

            var url = 'http://localhost:8080/fakebook/friends/requests/' + user.id;
            $.ajax({
                url: url,
                type: 'POST',
                error: function() {
                    console.log("error: " + url);
                },
                success: function(data) {
                    if(data === true){
                        console.log("success: " + url);
                    }
                    else {
                        console.log("error. already sent: " + url);
                    }
                    addFriendBtn.innerHTML = "Friend Request Sent";
                    addFriendBtn.disabled = true;
                }
            });
            return false;
        });

        $("#other_profile_sidebar_container").append(addFriendBtn);
        console.log("added add friend button");
    });
}

function loadAddPostToWall(user){
    var addPostDiv;
    $.get("html/add-post.html", function (data) {
        var wrapper = document.createElement('div');
        wrapper.innerHTML = data;
        addPostDiv = wrapper.firstChild;
        var jQaddPostDiv = $(addPostDiv);
        $(jQaddPostDiv).find('input.add-post-btn').on('click', function () {
            var text = $(jQaddPostDiv).find('textarea.add-post-text-area').val();
            var post = new Post(null, null, user, text, null, new Date().getTime());
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
                    loadOtherProfileSidebar(user);


                    return false;
                }
            });
        });

        $("#wall").append(addPostDiv);
    });
    console.log("added add post to wall");
}

function loadPostsToWall(user) {
    console.log('loadPostsToWall()');

    var url = "http://localhost:8080/fakebook/posts/" + user.id;

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
                            $(newCommentLi).find('span.like-count').text(value2.likes.length);

                            $(newCommentLi).find('button.comment-like-btn').on('click', function(){
                                console.log("like comment: " + value.commentId);
                                var url2 = "http://localhost:8080/fakebook/likes/comment/" + value2.commentId;
                                $.ajax({
                                    url: url2,
                                    type: 'POST',
                                    error: function () {
                                        console.log("error: " + url2);
                                    },
                                    success: function () {
                                        console.log("success: " + url2);
                                    }
                                });
                                loadOtherProfileSidebar(user);
                            });

                            if(index === post.comments.length - 1){
                                $(newCommentLi).find('br.comment-br').remove();

                            }
                            $(newPostDiv).find('div.comments-section').append(commentLi);
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

                        loadOtherProfileSidebar(user);
                    });

                    //TODO: add functionality: like posts
                    $(newPostDiv).find('button.post-like-btn').on('click', function () {
                        console.log("like post: " + post.postId);
                        var url2 = "http://localhost:8080/fakebook/likes/post/" + post.postId;
                        $.ajax({
                            url: url2,
                            type: 'POST',
                            error: function () {
                                console.log("error: " + url2);
                            },
                            success: function () {
                                console.log("success: " + url2);
                            }
                        });

                        loadOtherProfileSidebar(user);
                    });

                    $('#wall').append(newPostDiv);
                });
            });
            return false;
        }
    });

}

function loadWall(){
    $.get("html/wall.html", function (data) {
        var wrapper = document.createElement('div');
        wrapper.innerHTML = data;
         var wallDiv = wrapper.firstChild;
        $("#middle-body-space").empty();
        $("#middle-body-space").append(wallDiv);
        console.log("added wall");
    });
}