$('#treeMenu').on('click', 'a', function() {
    $('#treeMenu li.active').removeClass('active');
    $(this).closest('li').addClass('active');
});
function addone() {
    var formData = new FormData();
    var files = document.getElementById("gimgpath").files;
    var imgpath = files[0];
    formData.append("gimgpath", imgpath);
    formData.append("scId",$('#secondarycateselect').val())
    formData.append("goodsName", $('#name').val())
    formData.append("goodsPrice", $('#price').val())
    formData.append("goodsStatus", $('#status').val())
    formData.append("goodsIntroduce", $('#introduce').val())
    formData.append("goodsStock", $('#stock').val())
    $.ajax({
        type: "post",
        url: "/goods/addone",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            if (data==1){
                success("成功加入零食")
            }else {
                fail("上架零食失败")
            }

        }
    });
}
