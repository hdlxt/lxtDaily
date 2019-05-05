package com.mbyte.easy.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mbyte.easy.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *

 * @since 2019-04-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_person")
public class Person extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private String photo;

    private double lon;

    private double lat;

    private LocalDateTime creTime;


}
