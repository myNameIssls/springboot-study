package org.hibernate.cfg;

import org.hibernate.AnnotationException;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.Target;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.boot.MappingException;
import org.hibernate.boot.jaxb.Origin;
import org.hibernate.boot.jaxb.SourceType;
import org.hibernate.cfg.annotations.HCANNHelper;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.internal.util.StringHelper;
import org.jboss.logging.Logger;

import javax.persistence.*;
import java.util.*;

/**
 * 解决SpringDataJpa实体类中属性顺序与数据库中生成字段顺序不一致的问题
 */
class PropertyContainer {
    private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, PropertyContainer.class.getName());
    private final XClass xClass;
    private final XClass entityAtStake;
    private final org.hibernate.cfg.AccessType classLevelAccessType;
    private final LinkedHashMap<String, XProperty> persistentAttributeMap;

    PropertyContainer(XClass clazz, XClass entityAtStake, org.hibernate.cfg.AccessType defaultClassLevelAccessType) {
        this.xClass = clazz;
        this.entityAtStake = entityAtStake;
        if (defaultClassLevelAccessType == org.hibernate.cfg.AccessType.DEFAULT) {
            defaultClassLevelAccessType = org.hibernate.cfg.AccessType.PROPERTY;
        }

        org.hibernate.cfg.AccessType localClassLevelAccessType = this.determineLocalClassDefinedAccessStrategy();

        assert localClassLevelAccessType != null;

        this.classLevelAccessType = localClassLevelAccessType != org.hibernate.cfg.AccessType.DEFAULT ? localClassLevelAccessType : defaultClassLevelAccessType;

        assert this.classLevelAccessType == org.hibernate.cfg.AccessType.FIELD || this.classLevelAccessType == org.hibernate.cfg.AccessType.PROPERTY;

        this.persistentAttributeMap = new LinkedHashMap<>();
        List<XProperty> fields = this.xClass.getDeclaredProperties(org.hibernate.cfg.AccessType.FIELD.getType());
        List<XProperty> getters = this.xClass.getDeclaredProperties(org.hibernate.cfg.AccessType.PROPERTY.getType());
        this.preFilter(fields, getters);
        Map<String, XProperty> persistentAttributesFromGetters = new HashMap();
        this.collectPersistentAttributesUsingLocalAccessType(this.persistentAttributeMap, persistentAttributesFromGetters, fields, getters);
        this.collectPersistentAttributesUsingClassLevelAccessType(this.persistentAttributeMap, persistentAttributesFromGetters, fields, getters);
    }

    private void preFilter(List<XProperty> fields, List<XProperty> getters) {
        Iterator propertyIterator = fields.iterator();

        XProperty property;
        while(propertyIterator.hasNext()) {
            property = (XProperty)propertyIterator.next();
            if (mustBeSkipped(property)) {
                propertyIterator.remove();
            }
        }

        propertyIterator = getters.iterator();

        while(propertyIterator.hasNext()) {
            property = (XProperty)propertyIterator.next();
            if (mustBeSkipped(property)) {
                propertyIterator.remove();
            }
        }

    }

    private void collectPersistentAttributesUsingLocalAccessType(LinkedHashMap<String, XProperty> persistentAttributeMap, Map<String, XProperty> persistentAttributesFromGetters, List<XProperty> fields, List<XProperty> getters) {
        Iterator propertyIterator = fields.iterator();

        XProperty xProperty;
        Access localAccessAnnotation;
        while(propertyIterator.hasNext()) {
            xProperty = (XProperty)propertyIterator.next();
            localAccessAnnotation = (Access)xProperty.getAnnotation(Access.class);
            if (localAccessAnnotation != null && localAccessAnnotation.value() == javax.persistence.AccessType.FIELD) {
                propertyIterator.remove();
                persistentAttributeMap.put(xProperty.getName(), xProperty);
            }
        }

        propertyIterator = getters.iterator();

        while(propertyIterator.hasNext()) {
            xProperty = (XProperty)propertyIterator.next();
            localAccessAnnotation = (Access)xProperty.getAnnotation(Access.class);
            if (localAccessAnnotation != null && localAccessAnnotation.value() == javax.persistence.AccessType.PROPERTY) {
                propertyIterator.remove();
                String name = xProperty.getName();
                XProperty previous = (XProperty)persistentAttributesFromGetters.get(name);
                if (previous != null) {
                    throw new MappingException(LOG.ambiguousPropertyMethods(this.xClass.getName(), HCANNHelper.annotatedElementSignature(previous), HCANNHelper.annotatedElementSignature(xProperty)), new Origin(SourceType.ANNOTATION, this.xClass.getName()));
                }

                persistentAttributeMap.put(name, xProperty);
                persistentAttributesFromGetters.put(name, xProperty);
            }
        }

    }

    private void collectPersistentAttributesUsingClassLevelAccessType(LinkedHashMap<String, XProperty> persistentAttributeMap, Map<String, XProperty> persistentAttributesFromGetters, List<XProperty> fields, List<XProperty> getters) {
        Iterator var5;
        XProperty getter;
        if (this.classLevelAccessType == org.hibernate.cfg.AccessType.FIELD) {
            var5 = fields.iterator();

            while(var5.hasNext()) {
                getter = (XProperty)var5.next();
                if (!persistentAttributeMap.containsKey(getter.getName())) {
                    persistentAttributeMap.put(getter.getName(), getter);
                }
            }
        } else {
            var5 = getters.iterator();

            while(var5.hasNext()) {
                getter = (XProperty)var5.next();
                String name = getter.getName();
                XProperty previous = (XProperty)persistentAttributesFromGetters.get(name);
                if (previous != null) {
                    throw new MappingException(LOG.ambiguousPropertyMethods(this.xClass.getName(), HCANNHelper.annotatedElementSignature(previous), HCANNHelper.annotatedElementSignature(getter)), new Origin(SourceType.ANNOTATION, this.xClass.getName()));
                }

                if (!persistentAttributeMap.containsKey(name)) {
                    persistentAttributeMap.put(getter.getName(), getter);
                    persistentAttributesFromGetters.put(name, getter);
                }
            }
        }

    }

    public XClass getEntityAtStake() {
        return this.entityAtStake;
    }

    public XClass getDeclaringClass() {
        return this.xClass;
    }

    public org.hibernate.cfg.AccessType getClassLevelAccessType() {
        return this.classLevelAccessType;
    }

    public Collection<XProperty> getProperties() {
        this.assertTypesAreResolvable();
        return Collections.unmodifiableCollection(this.persistentAttributeMap.values());
    }

    private void assertTypesAreResolvable() {
        Iterator var1 = this.persistentAttributeMap.values().iterator();

        XProperty xProperty;
        do {
            if (!var1.hasNext()) {
                return;
            }

            xProperty = (XProperty)var1.next();
        } while(xProperty.isTypeResolved() || discoverTypeWithoutReflection(xProperty));

        String msg = "Property " + StringHelper.qualify(this.xClass.getName(), xProperty.getName()) + " has an unbound type and no explicit target entity. Resolve this Generic usage issue or set an explicit target attribute (eg @OneToMany(target=) or use an explicit @Type";
        throw new AnnotationException(msg);
    }

    private org.hibernate.cfg.AccessType determineLocalClassDefinedAccessStrategy() {
        org.hibernate.cfg.AccessType hibernateDefinedAccessType = org.hibernate.cfg.AccessType.DEFAULT;
        org.hibernate.cfg.AccessType jpaDefinedAccessType = org.hibernate.cfg.AccessType.DEFAULT;
        org.hibernate.annotations.AccessType accessType = (org.hibernate.annotations.AccessType)this.xClass.getAnnotation(org.hibernate.annotations.AccessType.class);
        if (accessType != null) {
            hibernateDefinedAccessType = org.hibernate.cfg.AccessType.getAccessStrategy(accessType.value());
        }

        Access access = (Access)this.xClass.getAnnotation(Access.class);
        if (access != null) {
            jpaDefinedAccessType = org.hibernate.cfg.AccessType.getAccessStrategy(access.value());
        }

        if (hibernateDefinedAccessType != org.hibernate.cfg.AccessType.DEFAULT && jpaDefinedAccessType != org.hibernate.cfg.AccessType.DEFAULT && hibernateDefinedAccessType != jpaDefinedAccessType) {
            throw new org.hibernate.MappingException("@AccessType and @Access specified with contradicting values. Use of @Access only is recommended. ");
        } else {
            org.hibernate.cfg.AccessType classDefinedAccessType;
            if (hibernateDefinedAccessType != org.hibernate.cfg.AccessType.DEFAULT) {
                classDefinedAccessType = hibernateDefinedAccessType;
            } else {
                classDefinedAccessType = jpaDefinedAccessType;
            }

            return classDefinedAccessType;
        }
    }

    private static boolean discoverTypeWithoutReflection(XProperty p) {
        if (p.isAnnotationPresent(OneToOne.class) && !((OneToOne)p.getAnnotation(OneToOne.class)).targetEntity().equals(Void.TYPE)) {
            return true;
        } else if (p.isAnnotationPresent(OneToMany.class) && !((OneToMany)p.getAnnotation(OneToMany.class)).targetEntity().equals(Void.TYPE)) {
            return true;
        } else if (p.isAnnotationPresent(ManyToOne.class) && !((ManyToOne)p.getAnnotation(ManyToOne.class)).targetEntity().equals(Void.TYPE)) {
            return true;
        } else if (p.isAnnotationPresent(ManyToMany.class) && !((ManyToMany)p.getAnnotation(ManyToMany.class)).targetEntity().equals(Void.TYPE)) {
            return true;
        } else if (p.isAnnotationPresent(Any.class)) {
            return true;
        } else if (p.isAnnotationPresent(ManyToAny.class)) {
            if (!p.isCollection() && !p.isArray()) {
                throw new AnnotationException("@ManyToAny used on a non collection non array property: " + p.getName());
            } else {
                return true;
            }
        } else if (p.isAnnotationPresent(Type.class)) {
            return true;
        } else {
            return p.isAnnotationPresent(Target.class);
        }
    }

    private static boolean mustBeSkipped(XProperty property) {
        return property.isAnnotationPresent(Transient.class) || "net.sf.cglib.transform.impl.InterceptFieldCallback".equals(property.getType().getName()) || "org.hibernate.bytecode.internal.javassist.FieldHandler".equals(property.getType().getName());
    }
}