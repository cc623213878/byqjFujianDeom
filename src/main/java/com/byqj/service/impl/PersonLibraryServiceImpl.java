package com.byqj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssPersonLibraryDao;
import com.byqj.entity.TssPersonLibrary;
import com.byqj.service.IPersonLibraryService;
import org.springframework.stereotype.Service;

/**
 * @ClassName:PersonLibraryServicelmpl
 * @Description:
 * @Author:lwn
 * @Date:2019/3/22 16:30
 **/
@Service(value = "personLibraryService")
public class PersonLibraryServiceImpl extends ServiceImpl<TssPersonLibraryDao, TssPersonLibrary>
        implements IPersonLibraryService {
}
