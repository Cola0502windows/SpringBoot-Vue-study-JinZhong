package cola.study.entity;

import java.time.LocalDateTime;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: BaseEntity
 * @date 2023/5/13 17:09
 */
public class BaseEntity {
    /**
     * 创建人
     */

    private Long createdBy;

    /**
     * 创建时间
     */

    private LocalDateTime createdTime;

    /**
     * 修改人
     */
    private Long updatedBy;

    /**
     * 修改时间
     */
    private LocalDateTime updatedTime;

    /**
     * 逻辑删除字段 0-未删除 1-已删除
     */
    private Integer isDeleted;

    /**
     * 备注
     */
    private String remark;
}
