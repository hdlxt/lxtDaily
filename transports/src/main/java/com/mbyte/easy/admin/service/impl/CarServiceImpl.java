package com.mbyte.easy.admin.service.impl;

import com.mbyte.easy.admin.entity.Car;
import com.mbyte.easy.admin.mapper.CarMapper;
import com.mbyte.easy.admin.service.ICarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 黄润宣
 * @since 2019-04-10
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements ICarService {

}
