package com.qzh.shop.service.impl;

import com.qzh.shop.POJO.DO.Attribute;
import com.qzh.shop.POJO.DO.FirstCategories;
import com.qzh.shop.POJO.DO.Parameter;
import com.qzh.shop.mapper.ParameterMapper;
import com.qzh.shop.service.ParameterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/6/14 0:16
 * @Version 1.0
 */
@Service
public class ParameterServiceImpl implements ParameterService {

    @Resource
    ParameterMapper parameterMapper;

    @Override
    public List<FirstCategories> getAllCategories(FirstCategories firstCategories) {
        return parameterMapper.getAllCategories(firstCategories);
    }

    @Override
    public List<Parameter> getAllParameterById(Integer pId) {
        return parameterMapper.getAllParameterById(pId);
    }

    @Override
    public List<Attribute> getAllAttributeById(Integer pId) {
        return parameterMapper.getAllAttributeById(pId);
    }

    @Override
    public int addParameterByPid(Integer pId, String parameter) {
        return parameterMapper.insertParameterByPid(pId,parameter);
    }

    @Override
    public int addAttrbuteByPid(Integer pId, String attribute) {
        return parameterMapper.insertAttributeByPid(pId,attribute);
    }

    @Override
    public Parameter getParameterById(Integer id) {
        return parameterMapper.getParameterById(id);
    }

    @Override
    public Attribute getAttributeById(Integer id) {
        return parameterMapper.getAttributeById(id);
    }

    @Override
    public int updataParameterById(Integer id, String parameter) {
        return parameterMapper.updataParameterById(id,parameter);
    }

    @Override
    public int updataAttributeById(Integer id, String attribute) {
        return parameterMapper.updataAttributeById(id,attribute);
    }

    @Override
    public int deleteParameterById(Integer id) {
        return parameterMapper.deleteParameterById(id);
    }

    @Override
    public int deleteAttributeById(Integer id) {
        return parameterMapper.deleteAttributeById(id);
    }
}
