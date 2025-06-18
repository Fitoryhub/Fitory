const express=require('express');
const app=express();
const port=3000;

const fsearch=require('./foodsearch');
const access_token=require('./Access_token');


let token=access_token.token;

app.get('/getdata',async(request,response)=>{
   try{
    const tkdata= await access_token.token();
    const token=tkdata.access_token;
    fsearch.settk(token);
    fsearch.setfn(request.query.foodname);
    const rdata= await fsearch.getdata();
    response.status(200).json(rdata);
   }catch(err){
    response.status(500).send('오류');
    console.log(errr);
   }
})


app.listen(port,()=>{
    console.log('Server running');
})

