package com.service.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.service.file.doMain.FileResource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<FileResource> {
}
