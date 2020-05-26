// function sendMessage(pageId, text) {
//     let body = {
//         pageId: pageId,
//         text: text
//     };
//
//     $.ajax({
//         url: "/messages",
//         method: "POST",
//         data: JSON.stringify(body),
//         contentType: "application/json",
//         dataType: "json",
//         complete: function () {
//             if (text === 'Login') {
//                 receiveMessage(pageId)
//             }
//         }
//     });
// }
//
// // LONG POLLING
// function receiveMessage(pageId) {
//     $.ajax({
//         url: "/messages?pageId=" + pageId,
//         method: "GET",
//         dataType: "json",
//         contentType: "application/json",
//         success: function (response) {
//             $('#messages').first().after('<li>' + response[0]['text'] + '</li>');
//             receiveMessage(pageId);
//         }
//     })
// }
function sendMessage(pageId, userId, text) {
    let body = {
        pageId: pageId,
        text: text,
        userId: userId
    };

    $.ajax({
        url: "/messages",
        method: "POST",
        data: JSON.stringify(body),
        contentType: "application/json",
        dataType: "json",
        complete: function () {
            if (text === 'Login') {
                receiveMessage(pageId)
            }
        }
    });
}

// LONG POLLING
function receiveMessage(pageId) {
    $.ajax({
        url: "/messages?pageId=" + pageId,
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            // console.log(response)
            $('#messages').first().append('<tr> <td>' + response[0]['user']['userId'] + '</td><td>' + response[0]['message'] + '</td></tr>');
            receiveMessage(pageId);
        }
    })
}