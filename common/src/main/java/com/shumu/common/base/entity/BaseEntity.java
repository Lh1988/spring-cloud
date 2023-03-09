package com.shumu.common.base.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * @author Li
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity  implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(title="主键",type="String")
    private String id;
    @Schema(title ="状态",type ="Integer")
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    @Schema(title ="创建者",type ="String")
    private String createBy;
    @TableField(fill = FieldFill.UPDATE)
    @Schema(title ="更新者",type ="String")
    private String updateBy;
    @TableField(fill = FieldFill.INSERT)
    @Schema(title ="创建时间",type ="Date")
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.UPDATE)
    @Schema(title ="更新时间",type ="Date")
    private LocalDateTime updateTime;
}
