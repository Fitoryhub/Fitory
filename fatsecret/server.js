const express=require('express');
const cors=require('cors');
const app=express();
const port=3000;


const fsearch=require('./foodsearch');
const access_token=require('./Access_token');
const fcategories=require('./food_categories');
const fsubcategories=require('./fooo_subcategories')

app.use(cors({
    origin:true
}))

app.get('/getdata',async(request,response)=>{
   try{
        const tkdata= await access_token.token();
        const token=tkdata.access_token;
        fsearch.settk(token);
        fsearch.setfn(request.query.foodname);
        fsearch.setpg(request.query.page);
        const rdata= await fsearch.getdata();
        response.status(200).json(rdata);
   }catch(err){
        response.status(500).send('오류');
        console.log(err);
   }
})

app.get('/getcategories',async(request,response)=>{
    try{
        const tkdata= await access_token.token();
        const token=tkdata.access_token;
        fcategories.settk(token);
        const rdata= await fcategories.getdata();
        response.status(200).json(rdata);
    }catch(err){
        response.status(500).send('오류');
        console.log(err);
   }
})

app.get('/getsubcategories',async(request,response)=>{
    try{
        const tkdata= await access_token.token();
        const token=tkdata.access_token;
        fsubcategories.settk(token);
        fsubcategories.setct(request.query.foodct)
        const rdata= await fsubcategories.getdata();
        response.status(200).json(rdata);
    }catch{
        response.status(500).send('오류');
        console.log(error);
    }
})


app.listen(port,()=>{
    console.log('Server running');
})

