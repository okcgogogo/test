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
            }else{
                alert(response.message);
            }
        },
        dataType:"json"
    })
}