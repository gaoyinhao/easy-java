package com.easyjava;

import com.easyjava.bean.TableInfo;
import com.easyjava.builder.*;

import java.util.List;

/**
 * @author gao98
 */
public class RunApplication {
    public static void main(String[] args) {
        //获取所有的表
        List<TableInfo> tableInfoList = BuildTable.getTables();
        //将template下的
        BuildBase.execute();
        for (TableInfo tableInfo : tableInfoList) {
            //生成PO
            BuildPo.execute(tableInfo);
            //生成POQuery
            BuildQuery.execute(tableInfo);
            //生成Mapper
            BuildMapper.execute(tableInfo);
            //生成MapperXml
            BuildMapperXml.execute(tableInfo);
            //生成Service
            BuildService.execute(tableInfo);
            //生成ServiceImpl
            BuildServiceImpl.execute(tableInfo);
            //生成Controller
            BuildController.execute(tableInfo);
        }
    }
}
