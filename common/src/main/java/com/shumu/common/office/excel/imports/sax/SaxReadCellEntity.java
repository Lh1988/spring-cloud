package com.shumu.common.office.excel.imports.sax;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Li
 * @since 22:19 2021/8/16 0016
 */
@Data
@AllArgsConstructor
public class SaxReadCellEntity {
    /**值类型*/
    private CellValueType cellType;
    /**值*/
    private Object value;
}
