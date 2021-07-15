$(document).ready(function() {
    $('#datalist').DataTable();
    document.getElementById("datalist_next").innerHTML = "";
    document.getElementById("datalist_previous").innerHTML = "";
    let img = document.createElement("img");
    img.src = "/images/prev.svg";
    img.style.verticalAlign = "bottom";
    document.getElementById("datalist_previous").appendChild(img);
    img = document.createElement("img");
    img.src = "/images/next.svg";
    img.style.verticalAlign = "bottom";
    document.getElementById("datalist_next").appendChild(img);
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