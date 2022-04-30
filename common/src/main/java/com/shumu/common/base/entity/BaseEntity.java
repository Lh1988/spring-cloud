package com.shumu.common.base.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Li
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity  implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value ="主键",dataType ="String")
    private String id;
    @ApiModelProperty(value ="状态",dataType ="Integer")
    private Integer status;
    @ApiModelProperty(value ="创建者",dataType ="String")
    private String createBy;
    @ApiModelProperty(value ="更新者",dataType ="String")
    private String updateBy;
    @ApiModelProperty(value ="创建时间",dataType ="Date")
    private LocalDateTime createTime;
    @ApiModelProperty(value ="更新时间",dataType ="Date")
    private LocalDateTime updateTime;
}
