function formatTimestamp( timestamp ) {
    var dateObj = new Date( timestamp );

    var year = dateObj.getYear() + 1900;
    var month = dateObj.getMonth() + 1;
    var theDate = dateObj.getDate();
    var hour = dateObj.getHours();
    var minute = dateObj.getMinutes();
    var second = dateObj.getSeconds();
    return year +"-"+ month +"-" + theDate + " "+ hour +":"+ minute +":"+ second;
}