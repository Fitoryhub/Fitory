var request = require("request");
clientID = '59be412169d845c1a8e581bdeb5c3715';
clientSecret = '45c02ba264d849db9d002927086ca5ec';

function getTK(){
   return new Promise((resoleve,reject)=>{
      var options = {
         method: 'POST',
         url: 'https://oauth.fatsecret.com/connect/token',
         method : 'POST',
         auth : {
            user : clientID,
            password : clientSecret
         },
         headers: { 'content-type': 'application/x-www-form-urlencoded'},
         form: {
            'grant_type': 'client_credentials',
            'scope' : 'premier'
         },
         json: true
      };
      request(options, function (error, response, body) {
         if (error) throw new Error(error);
         console.log(body);
         resoleve(body);
      });
   })
}

module.exports.token=getTK;