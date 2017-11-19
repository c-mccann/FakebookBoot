function loadSearchBar(){
    $('#middle-header-space').load('html/search-bar.html',function(){console.log("search bar loaded")});
}

function loadProfileSidebar(userJson) {
    $.get("html/profile-sidebar.html", function (data) {
        var user = JSON.parse(userJson);

        var wrapper= document.createElement('div');
        wrapper.innerHTML = data;
        var profileSidebarContainer = wrapper.firstChild;

        profileSidebarContainer.innerHTML =
            profileSidebarContainer.innerHTML.replace("fname lname", user.firstName + " " + user.lastName);

        $('#left-body-space').empty();
        $('#left-body-space').append(profileSidebarContainer);
        console.log("profile sidebar loaded");
    });
    loadViewFriendsBtn(userJson);
    loadFeed();
    loadAddPostToFeed(JSON.parse(userJson));
    loadPostsToFeed(JSON.parse(userJson));

    // TODO: think about adding functions here like loadOtherProfileSidebar()
}

function loadViewFriendsBtn(userJson){
    $.get("html/view-friends-btn.html", function (data) {
        var user = JSON.parse(userJson);
        var wrapper= document.createElement('div');
        wrapper.innerHTML = data;
        var loadViewFriendsBtn = wrapper.firstChild;

         $(loadViewFriendsBtn).on('click', function () {

            if(loadViewFriendsBtn.innerHTML.trim() === "View Friends"){
                $("#middle-body-space").empty();
                loadFriendRequestsBanner();
                loadFriendRequests();
                loadFriendsBanner();
                loadFriends(loadViewFriendsBtn, user);
                console.log("TODO: load friends, remove feed");
            }
            else if (loadViewFriendsBtn.innerHTML.trim() === "View Feed"){

                loadViewFriendsBtn.innerHTML = "View Friends";
                console.log("TODO: load feed, remove friends");
                isLoggedIn();
            }
            return false;
        });
        $("#profile_sidebar_container").append(loadViewFriendsBtn);
        console.log("added view friends/view feed button");
        return false;

    });
}


function unloadSearchBar(){
    $("#middle-header-space").empty();
}


function unloadProfileSidebar(){
    $("#left-body-space").empty();
}


function settingsMenuLoadOnLogin(){
    $("#divider_1").hide();
    $("#divider_2").hide();
    $("#login_form_li").hide();
    $("#register_form_li").hide();
    $("#login_li").hide();
    $("#register_li").hide();

    $("#logout_li").show();
}


function settingsMenuLoadOnLogout(){
    $("#logout_form_li").hide();
    $("#logout_li").hide();
    $("#divider_3").hide();

    $("#divider_1").show();

    $("#login_li").show();
    $("#register_li").show();
}


function loadFriendRequests(){
    // TODO: load friend requests
    var url = 'http://localhost:8080/fakebook/friends/requests/all';
    $.ajax({
        url: url,
        type: 'GET',
        error: function() {
            console.log("error: " + url);
            return false;
        },
        success: function(data) {
            console.log("success: " + url);
            var friendRequestsList = JSON.parse(JSON.stringify(data));
            friendRequestsList.map(function (friendRequest) {
                console.log("\tall friend requests: " + JSON.stringify(friendRequest));
                $.get("html/search-result.html", function (data) {
                    var wrapper= document.createElement('div');
                    wrapper.innerHTML = data;
                    var searchResultHtml = wrapper.firstChild;
                    var jQsearchResultHtml = $(searchResultHtml);
                    // TODO: load profile photo
                    $(jQsearchResultHtml).find("img.search-result-profile-photo");
                    $(jQsearchResultHtml).find("div.search-result-name").text(friendRequest.userOne.firstName + " " + friendRequest.userOne.lastName);
                    $.get("html/friend-request-form.html", function (data) {
                        var wrapper= document.createElement('div');
                        wrapper.innerHTML = data;
                        var friendRequestForm = wrapper.firstChild;
                        var jQfriendRequestForm = $(friendRequestForm);


                        // TODO: attach functionality for accepting friend requests here

                        $(jQfriendRequestForm).find("input.accept-request-button").on('click', function () {
                            console.log("accept friend request from: " + JSON.stringify(friendRequest.userOne));
                            var url2 = "http://localhost:8080/fakebook/friends/requests/accept";
                            $.ajax({
                                url: url2,
                                type: 'POST',
                                data: JSON.stringify(friendRequest.userOne),
                                contentType: 'application/json; charset=utf-8;',
                                error: function () {
                                    console.log("error: " + url2);
                                    return false;
                                },
                                success: function () {
                                    console.log("success: " + url2);
                                    $("#view_friends_btn").text('View Friends');
                                    $("#view_friends_btn").trigger('click');
                                    return false;
                                }
                            });
                        });

                        // TODO: attach functionality for declining friend requests here

                        $(jQfriendRequestForm).find("input.decline-request-button").on('click', function () {
                            console.log("decline friend request from: " + JSON.stringify(friendRequest.userOne));
                            var url3 = "http://localhost:8080/fakebook/friends/requests/decline";
                            $.ajax({
                                url: url3,
                                type: 'POST',
                                data: JSON.stringify(friendRequest.userOne),
                                contentType: 'application/json; charset=utf-8;',
                                error: function () {
                                    console.log("error: " + url3);
                                    return false;
                                },
                                success: function () {
                                    console.log("success: " + url3);
                                    $("#view_friends_btn").text('View Friends');
                                    $("#view_friends_btn").trigger('click');
                                    return false;
                                }
                            });
                            return false;
                        });
                        $(jQsearchResultHtml.append(jQfriendRequestForm));


                    });

                    $(jQsearchResultHtml).find("div.search-result-name").text(friendRequest.userOne.firstName + " " + friendRequest.userOne.lastName);

                    $("#middle-body-space").find("div.friend-requests-holder").append(jQsearchResultHtml);

                });

            });
            return false;
        }
    });
}

function loadFriendsBanner(){
    $.get("html/friends.html", function (data) {
        var wrapper= document.createElement('div');
        wrapper.innerHTML = data;
        var friendsDiv = wrapper.firstChild;
        var holderDiv = document.createElement('div');
        holderDiv.setAttribute('class', 'friends-holder');
        var jQholderDiv = $(holderDiv);
        $(jQholderDiv).append(friendsDiv);
        $("#middle-body-space").append(jQholderDiv);
    });
}

function loadFriendRequestsBanner() {
    $.get("html/friend-requests.html", function (data) {
        var wrapper= document.createElement('div');
        wrapper.innerHTML = data;
        var friendsRequestsDiv = wrapper.firstChild;
        var holderDiv = document.createElement('div');
        holderDiv.setAttribute('class', 'friend-requests-holder');
        var jQholderDiv = $(holderDiv);
        $(jQholderDiv).append(friendsRequestsDiv);
        $("#middle-body-space").append(jQholderDiv);
    });
}

function loadFriends(loadViewFriendsBtn, user){
    var url = 'http://localhost:8080/fakebook/friends/' + user.id;
    $.ajax({
        url: url,
        type: 'GET',
        error: function() {
            console.log("error: " + url);
        },
        success: function(data) {
            console.log("success: " + url);
            console.log(JSON.stringify(data));
            var friendsList = JSON.parse(JSON.stringify(data));

            friendsList.map(function (friend) {
                $.get("html/search-result.html", function (data) {
                    var wrapper= document.createElement('div');
                    wrapper.innerHTML = data;
                    var searchResultHtml = wrapper.firstChild;
                    var jQsearchResultHtml = $(searchResultHtml);
                    // TODO: load profile photo
                    $(jQsearchResultHtml).find("img.search-result-profile-photo");
                    $(jQsearchResultHtml).find("div.search-result-name").text(friend.firstName + " " + friend.lastName);

                    $("#middle-body-space").find("div.friends-holder").append(jQsearchResultHtml);

                });
                console.log("friend: " + friend.toString());
            });
            loadViewFriendsBtn.innerHTML = "View Feed";
        }
    });
}

