function success(data){
    var registsuccessMessager = new $.zui.Messager('提示消息: '+data+'!', {
        type: 'success',
        icon: 'check'
    }).show();

}

function fail(data) {
    var registfailMessager = new $.zui.Messager('提示消息: '+data+'!', {
        type: 'success',
        icon: 'times'
    }).show();
}
$('#treeMenu').on('click', 'a', function() {
    $('#treeMenu li.active').removeClass('active');
    $(this).closest('li').addClass('active');
});