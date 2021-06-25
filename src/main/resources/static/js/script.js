$(document).ready(function() {
    $('#datalist').DataTable();
} );

function deleteEntity(id, type) {
    let url;
    switch (type) {
        case 'user':
            url = '/admin/user/' + id + '/delete';
            break;
        case 'student':
            url = '/admin/student/' + id + '/delete';
            break;
        case 'class':
            url = '/admin/class/' + id + '/delete';
            break;
        case 'subject':
            url = '/admin/subject/' + id + '/delete';
            break;
    }

    let result = confirm("Delete " + type + "?");
    if (result) {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

        $.ajax({
            url: url,
            type: 'delete',
        });
        document.location.reload();
    }
}