package com.demo.user.Service;

import com.Core.DoMain.AjaxResult;
import com.demo.user.DoMain.Dto.*;
import com.demo.user.DoMain.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    User SecurityQueryById(String userId);

    AjaxResult adminListByIsDel(AdminUserListDto dto);

    AjaxResult adminAdd(AdminUserAddDto dto);

    AjaxResult adminEdit(AdminUserEditDto dto);

    AjaxResult adminDelete(AdminUserDeleteDto dto);

    AjaxResult adminGetRight(AdminUserDeleteDto dto);
}
