package toy.toyproject2.domain.entity.auditing;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuditingDateBy is a Querydsl query type for AuditingDateBy
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAuditingDateBy extends EntityPathBase<AuditingDateBy> {

    private static final long serialVersionUID = -169497557L;

    public static final QAuditingDateBy auditingDateBy = new QAuditingDateBy("auditingDateBy");

    public final QAuditingDate _super = new QAuditingDate(this);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public QAuditingDateBy(String variable) {
        super(AuditingDateBy.class, forVariable(variable));
    }

    public QAuditingDateBy(Path<? extends AuditingDateBy> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuditingDateBy(PathMetadata metadata) {
        super(AuditingDateBy.class, metadata);
    }

}

