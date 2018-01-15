var async = require("async");
var now = require("performance-now");

var Fork = function() {
    this.state = 0;
    return this;
};

Fork.prototype.acquire = function(id, fork, callback) {

    var self = this;
    var t0 = now();
    var check = function(n) {
    		if(self.state === 0) {
    			self.state = 1;
    			var t1= now();
    			console.log((t1-t0).toFixed(3));
    			callback();
    		}
    		else {
    			setTimeout(check,n,2*n);
    			//console.log("Philosopher nr " + id + " waiting ");
    		}
    	};
    	setTimeout(check,1,1);

};

Fork.prototype.release = function() {
    this.state = 0;
}

var Conductor = function() {
    this.state = 0;
    return this;
};

var Conductors = function(N) {
    this.conductors = [];
    this.size = N-1;
    for (var i = 0; i < N-1; i++) {
        this.conductors.push(new Conductor());
    }
    return this;
};

Conductors.prototype.acquire = function(id, callback) {

    var self = this;
    var t0 = now();
    var check = function(n) {
            var cont = true;
    	    for(var i = 0; i<self.size && cont;i++) {
    		    if(self.conductors[i].state === 0) {
                   self.conductors[i].state = 1;
                   cont = false;
                   var t1= now();
                   //console.log((t1-t0).toFixed(3));
                   //console.log("Philosopher nr " + id + " entered ");
                   callback();
                }
            }

            if(cont===true) {
                setTimeout(check,n,2*n);
            }
          }
    	setTimeout(check,1,1);
};

Conductors.prototype.release = function() {
    var self = this;
    var cont = true;
    for(var i = 0; i<self.size && cont;i++) {
        if(self.conductors[i].state === 1) {
            self.conductors[i].state = 0;
            //console.log("Philosopher go out ");
            cont = false;
        }
    }
}

var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    return this;
}

var Philosopher = function(id, forks) {
	this.id = id;
	this.forks = forks;
	this.f1 = id % forks.length;
	this.f2 = (id + 1) % forks.length;
	return this;
};

Philosopher.prototype.startNaive = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    for (var i = 0; i < count; i++) {
        forks[f1].acquire(id,f1, function() {
        //console.log("Philosopher nr "+ id + "took left fork nr"+ f1);

        forks[f2].acquire(id, f2, function() {
        				//console.log("Philosopher nr " +id + " took right fork nr " + f2);
        				//console.log("Philosopher nr " + id + " start eating");

        				setTimeout(function() {
        					forks[f1].release();
        					//console.log("Philosopher nr " + id + " released fork nr " + f1);
        					forks[f2].release();
        					//console.log("Philosopher nr " + id + " released fork nr " + f2);
        				},1000);
        			});
        });
    }
}

Philosopher.prototype.startAsym = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    	var first_fork;
    	var second_fork;


    	for(var i=0; i<count; i++) {
    		if(id % 2 === 0) {
    			first_fork = f2;
    			second_fork = f1;
    		}
    		else {
    			first_fork = f1;
    			second_fork = f2;
    		}

    		forks[first_fork].acquire(id, first_fork, function() {

    		    //console.log("Philosopher nr "+ id + " took fork nr "+ first_fork);

    			forks[second_fork].acquire(id, second_fork, function() {
    				//console.log("Philosopher nr "+ this.id + " took fork nr " + second_fork);
    				//console.log("Philosopher nr " + this.id + " start eating");

    				setTimeout(function() {
    					forks[first_fork].release();
    					//console.log("Philosopher nr " + id + " release fork nr " + first_fork);
    					forks[second_fork].release();
    					//console.log("Philosopher nr " + id + " release fork nr " + second_fork);
    				},1000)
    			});
    		});
    	}
};


function funDelay(cb) {
   var delay = 500;
   setTimeout(function() {
       if (cb) cb();
   }, delay);
}

Philosopher.prototype.startConductor = function(count, conductors) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    var loop = function() {
        funDelay(function(){

            conductors.acquire(id,function() {

            //console.log("Philosopher nr "+ id + " entered kitchen ");

            forks[f1].acquire(id,f1, function() {
            //console.log("Philosopher nr "+ id + "took left fork nr"+ f1);

            forks[f2].acquire(id, f2, function() {
                            //console.log("Philosopher nr " +id + " took right fork nr " + f2);
                            //console.log("Philosopher nr " + id + " start eating");

                            setTimeout(function() {
                                forks[f1].release();
                                //console.log("Philosopher nr " + id + " released fork nr " + f1);
                                forks[f2].release();
                                //console.log("Philosopher nr " + id + " released fork nr " + f2);
                                conductors.release();
                            },1000);
                        });
            })
         })
        });
    }

  /*var tasks = [];
  for (var i = 0; i < count; i++) {
    tasks.push(loop);
  }
  tasks.push(function() {
      console.log('done'+ id);
  });

  async.waterfall(tasks);
  /*
  */
  for(var i = 0; i<count; i++)
  loop();

}

var N = 5;
var forks = [];

var philosophers = []
var conductors = new Conductors(N);

for (var i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

for (var i = 0; i < N; i++) {

     //philosophers[i].startNaive(3);
     //philosophers[i].startAsym(5);
     philosophers[i].startConductor(2, conductors);
}