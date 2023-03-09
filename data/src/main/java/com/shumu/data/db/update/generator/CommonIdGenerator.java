package com.shumu.data.db.update.generator;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.shumu.data.db.update.constant.UpdateDataConstant;

/**
 * @description:
 * @author: Li
 * @date: 2023-02-16
 */
public class CommonIdGenerator {
  private String databaseId;
  private int idType;
  private String sequence;

  public CommonIdGenerator(String databaseId,int idType, String sequence) {
    this.databaseId = databaseId;
    this.idType = idType;
    this.sequence = sequence;
  }

  public String getId() {
    String id = null;
    switch (idType) {
      case UpdateDataConstant.TYPE_NONE:
        id = null;
        break;
      case UpdateDataConstant.TYPE_AUTO:
        id = null;
        break;
      case UpdateDataConstant.TYPE_ASSIGN:
        id = IdWorker.getIdStr();
        break;
      case UpdateDataConstant.TYPE_UUID:
        id = IdWorker.get32UUID();
        break;
      case UpdateDataConstant.TYPE_SEQUENCE:
        switch (databaseId) {
          case UpdateDataConstant.DB_DB2:
            id = "values nextval for " + sequence;
            break;
          case UpdateDataConstant.DB_DM:
            id = "SELECT " + sequence + ".NEXTVAL FROM DUAL";
            break;
          case UpdateDataConstant.DB_FIREBIRD:
            id = "SELECT next value for " + sequence + " from rdb$database";
            break;
          case UpdateDataConstant.DB_H2:
            id = "select nextval('" + sequence + "')";
            break;
          case UpdateDataConstant.DB_KINGBASE:
            id = "select nextval('" + sequence + "')";
            break;
          case UpdateDataConstant.DB_ORACLE:
            id = "SELECT " + sequence + ".NEXTVAL FROM DUAL";
            break;
          case UpdateDataConstant.DB_POSTGRE:
            id = "select nextval('" + sequence + "')";
            break;
          case UpdateDataConstant.DB_SAP_HANA:
            id = "SELECT " + sequence + ".NEXTVAL FROM DUMMY";
            break;
          default:
            break;
        }
        break;
      default:
        break;
    }
    return id;
  }

}
