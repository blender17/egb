let currentDate = new Date();
document.getElementById('date').value = currentDate.toISOString().substring(0,10);


let table = new Tabulator("#gradebook", {
    minHeight: 400,
    layout: 'fitDataFill',
    headerHozAlign:"center",
    placeholder: "No data available",
    columns:[
        {title:"Name", field:"name", width: 200,},
    ],
});

for (let i = 0; i < students.length; i++) {
    table.addRow({name: students[i]}, false);
}

for (let i = 0; i < dates.length; i++) {
    table.addColumn({title:dates[i], field:dates[i], editor:"input", align:"center"}, false);
}

for (let i = 0; i < table.getDataCount(); i++) {
    let row = table.getRowFromPosition(i);
    for (let j = 0; j < gradebooks.length; j++) {
        if (row.getData().name == gradebooks[j].name) {
            row.update({[gradebooks[j].date]:gradebooks[j].value});
        }
    }
}


function insertColumnWithDate() {
    const date = document.getElementById("date").value;
    if (date !== '') {
        table.addColumn({title:date, field:date, editor:"input", align:"center"}, false);
    } else alert("Choose date")
}

async function saveData() {
    let data = [];
    const cols = table.getColumns();

    for (let i = 0; i < table.getDataCount(); i++) {
        let row = table.getRowFromPosition(i);
        for (let j = 1; j < cols.length; j++) {
            let value = row.getCell(cols[j]).getValue();
            if (value == undefined) {
                value = '';
            }
            data.push({name:row.getCell("name").getValue(), date:cols[j].getField(), value:value});
        }
    }

    const url = window.location.href;

    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(data)
    });
}