package toy.toyproject2.domain.entity.auditing;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuditingDate is a Querydsl query type for AuditingDate
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAuditingDate extends EntityPathBase<AuditingDate> {

    private static final long serialVersionUID = 312672436L;

    public static final QAuditingDate auditingDate = new QAuditingDate("auditingDate");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = createDateTime("lastModifiedDate", java.time.LocalDateTime.class);

    public QAuditingDate(String variable) {
        super(AuditingDate.class, forVariable(variable));
    }

    public QAuditingDate(Path<? extends AuditingDate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuditingDate(PathMetadata metadata) {
        super(AuditingDate.class, metadata);
    }

}

