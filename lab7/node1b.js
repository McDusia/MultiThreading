

var async = require('async');

/*function loop(n)
{
    if(n == 0)
     console.log('done!');
    else{
        async.waterfall([
        function(callback, n) {
            console.log('1');
            callback(null);
        },
        function(callback) {
                console.log('2');
                callback(null);
        },
        function(callback) {
                console.log('3');
                callback(null);
        }
        ], function (err, result) {
            loop(n-1);
        });
    }
}
*/
function waterfall(n)
{
    if(n == 0)
     console.log('done!');
    else{
        async.waterfall([
        function(callback, n) {
            console.log('1');
            callback(null);
        },
        function(callback) {
                console.log('2');
                callback(null);
        },
        function(callback) {
                console.log('3');
                callback(null);
        }
        ], function (err, result) {
            loop(n-1);
        });
    }
}


waterfall(6);

function printAsync(s, cb) {
   var delay = Math.floor((Math.random()*1000)+500);
   setTimeout(function() {
       console.log(s);
       if (cb) cb();
   }, delay);
}

function task1(cb) {
    printAsync("1", function() {
        task2(cb);
    });
}

function task2(cb) {
    printAsync("2", function() {
        task3(cb);
    });
}

function task3(cb) {
    printAsync("3", cb);
}


function task(cb, n) {
    if(n == 1)
        task1(cb);
    else
    {
        task1(function() { task(cb,n-1);});
    }
}

/*function loop(n) {
    task(function() {
        console.log('done!');}, n);
}

loop(10);
*/
//------ run x times atomic task ---------

function task11(cb, n) {
    if(n == 1) printAsync("1", cb);
    else {printAsync("1", function() {
        task22(cb, n-1);
        });
    }
}

function task22(cb,n) {
    if(n == 1) printAsync("2", cb);
    else {printAsync("2", function() {
        task33(cb, n-1);
    });
    }
}

function task33(cb,n) {
    if(n == 1) printAsync("3", cb);
    else{printAsync("3", function() {
                task11(cb, n-1);
            });
    }
}

function loop2(n) {
    task11(function() {
        console.log('done!');}, n);
}

//loop2(10);

/*
** Zadanie:
** Napisz funkcje loop(n), ktora powoduje wykonanie powyzszej 
** sekwencji zadan n razy. Czyli: 1 2 3 1 2 3 1 2 3 ... done
** 
*/
