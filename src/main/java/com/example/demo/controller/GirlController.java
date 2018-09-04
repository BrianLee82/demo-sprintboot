package com.example.demo.controller;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.demo.aspect.HttpAspect;
import com.example.demo.domain.Girl;
import com.example.demo.domain.Result;
import com.example.demo.repository.GirlRepository;
import com.example.demo.service.GirlService;
import com.example.demo.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@RestController
public class GirlController
{
    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Autowired
    private GirlRepository girlRepository;


    @Autowired
    private GirlService girlService;

    /*
    * 查询女生列表
    * */
    @GetMapping(value = "/girls")
    public List<Girl> girlList()
    {
        //System.out.println("girlList");
        logger.info("girlList");
        return girlRepository.findAll();
    }


    //增加一个女生
    @PostMapping(value = "/girls")
    public Result<Girl> girlAdd(@Valid Girl girl, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResultUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        girl.setCupSize(girl.getCupSize());
        girl.setAge(girl.getAge());

        return ResultUtil.success(girlRepository.save(girl));

    }

    //查询一个女生
    @GetMapping(value = "/girls/{id}")
    public Girl girlFindOne(@PathVariable("id") Integer id){
        //return girlRepository.getOne(id);
        return girlRepository.findById(id).get();
    }

    //更新一个女生
    @PutMapping(value = "/girls/{id}")
    public Girl girlUpdate(@PathVariable("id") Integer id,
                           @RequestParam("cupSize") String cupSize,
                           @RequestParam("age") Integer age)
    {
        Girl girl =new Girl();
        girl.setId(id);
        girl.setCupSize(cupSize);
        girl.setAge(age);

       return girlRepository.save(girl);

    }

    //删除一个女生
    @DeleteMapping(value = "/girls/{id}")
    public void girlDelete(@PathVariable("id") Integer id)
    {
        girlRepository.deleteById(id);
    }


    //通过年龄查询女生列表
    @GetMapping(value = "/girls/age/{age}")
    public List<Girl> girlListByAge(@PathVariable("age") Integer age){
        return girlRepository.findByAge(age);
    }


    @PostMapping(value = "/girls/two")
    public void girlTwo(){

        girlService.insertTwo();
    }

    @GetMapping(value = "/girls/getAge/{id}")
    public void getAge(@PathVariable("id") Integer id) throws Exception{
        girlService.getAge(id);
    }


}
