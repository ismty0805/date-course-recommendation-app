var express = require('express');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');

var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended : true}));
var port = process.env.PORT || 80;

mongoose.connect('mongodb://localhost:27017/date', {useFindAndModify:false});

app.listen(port, function () {
  console.log('server is running on' + port);
});


//스키마
var Schema_login = mongoose.Schema;
var Schema = mongoose.Schema;
var loginSchema = new Schema_login({
  userImg: String,
  name : String,
  userID : {type:String}, 
  userPassword : String,
  email : String,
  location: String,
  level: {type:String, default: 1}
});

var Login = mongoose.model("Login", loginSchema);

//전체 학생 명단
app.get('/logins', function (req, res, next){
  Login.find({}, function (error, login){
    if (error) {
      return res.json(error);
    }
    return res.json(login);
    });
});

app.delete('/logins/:userID', function (req, res, next){
  Login.remove({ "userID": req.params.userID}, function (error, login){
    if (error) {
      return res.json(error);
    }
    return res.json(login);
    });
});
app.delete('/logins/:id', function (req, res, next){
    Login.remove({ _id : req.params.id}, function (error, login){
      if (error) {
        return res.json(error);
      }
      return res.json(login);
      });
  });

app.get('/personalInfo/:userID', function(req, res, next){
    Login.find({"userID":req.params.userID}, function(error, login){
        if(error){
            return res.json(error);
        }
        return res.json(login);
    });
});


app.put('/changeArea/:userID', function(req, res, next){
    Login.update({"userID":req.params.userID}, {"location": req.body.location}, function(error, login){
        if(error){
            return res.json(error);
        }
        // console.log(""+req.params.location+"\n"+req.body.location+"\n"+req.params.userID+"\n"+req.body.userID);
        return res.json(login);
    });
});

app.put('/changeLevel/:userID', function(req, res, next){
    Login.update({"userID":req.params.userID}, {"level": req.body.level}, function(error, login){
        if(error){
            return res.json(error);
        }
        console.log(""+req.params.level+"\n"+req.body.level+"\n"+req.params.userID+"\n"+req.body.userID);
        return res.json(login);
    });
});

app.post('/logins', function(req, res, next){
  var userImg = req.body.userImg;
  var userID = req.body.userID;
  var userPassword = req.body.userPassword;
  var name = req.body.name;
  var email = req.body.email;
  var location = req.body.location;
  var level= req.body.level;
  var login = Login({
    userImg : userImg,
    userID : userID,
    userPassword : userPassword,
    name : name,
    email : email,
    location : location,
    level : level
  });

  login.save(function(err){
    if (err) {
      return res.json(err);
    }
    return res.send("Successfully Created");
  });
});


app.get('/logins/:userID', function(req, res, next){
  Login.find({userID: req.params.userID}, function(err, found){
    if(err){
      return res.json(err);
    }
    return res.send(found);
    });
});





var Schema_course = mongoose.Schema;
var courseSchema = new Schema_course({
  placeArray : Array,
  // latitudeArray : Array,
  // longitudeArray : Array,
  commentArray: Array,
  level : String,
  purpose : String,
  city : String
});
var Course = mongoose.model("Course", courseSchema);
app.get('/courses', function (req, res, next){
  Course.find({}, function (error, login){
    if (error) {
      return res.json(error);
    }
    return res.json(login);
    });
});
app.get('/courses:/:level', function (req, res, next){
  Course.find({level: req.params.level}, function (error, login){
    if (error) {
      return res.json(error);
    }
    return res.json(login);
    });
});

app.post('/courses', function(req, res, next){
  var placeArray = req.body.placeArray;
  // var latitudeArray = req.body.latitudeArray;
  // var longitudeArray = req.body.longitudeArray;
  var commentArray = req.body.commentArray;
  var level = req.body.level;
  var purpose = req.body.purpose;
  var city = req.body.city;
  var course = Course({
    placeArray : placeArray,
    // latitudeArray : latitudeArray,
    // longitudeArray : longitudeArray,
    commentArray : commentArray,
    level : level,
    purpose : purpose,
    city : city
  });

  course.save(function(err){
    if (err) {
      return res.json(err);
    }
    return res.send("Successfully Created");
  });
});
app.delete('/courses/:id', function (req, res, next){
  Course.remove({ _id : req.params.id}, function (error, login){
    if (error) {
      return res.json(error);
    }
    return res.json(login);
    });
});
app.put('/courses', function(req, res, next){
  Course.find({city: req.body.city, purpose:req.body.purpose, level: req.body.level}, function(error, docs){
    if(error){
      return res.json(error);
    }
    console.log(""+req.body.city+req.body.purpose+req.body.level)
    return res.json(docs);  
  });
});