function comment() {
    var questionId = $('#question_id').val();
    var content = $('#content').val();
    $.ajax({
        type:"post",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId" : questionId,
            "content" : content,
            "type" : 1
        }),
        success:function (response) {
            if(response.code == 200){
                $('#comment_section').hide();
            }else if(response.code == 2003){
                if(confirm(response.message)){
                    window.open("https://github.com/login/oauth/authorize?client_id=80f9cd77a96122b3b6d6&redirect_uri=http://localhost:8887/callback&state=1");
                    localStorage.setItem("closeable","true");
                }
            }else{
                alert(response.message);
            }
        },
        dataType:"json"
    })
}