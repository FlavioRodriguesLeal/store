package br.com.service.store.config.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

@Component
public class CascadingMongoEventListener extends AbstractMongoEventListener<Object> {

	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public void onBeforeConvert(BeforeConvertEvent<Object> source) {

		ReflectionUtils.doWithFields(source.getSource().getClass(), new ReflectionUtils.FieldCallback() {

			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {

				ReflectionUtils.makeAccessible(field);

				if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {

					Object fieldValue = field.get(source.getSource());
					if(fieldValue == null) return;
					
					DbRefFieldCallback callback = new DbRefFieldCallback();
					
					if (fieldValue.getClass().equals(ArrayList.class)) {
						ParameterizedType genericType = (ParameterizedType) field.getGenericType();
						Class<?> clazz = (Class<?>) genericType.getActualTypeArguments()[0];
						ReflectionUtils.doWithFields(clazz, callback);
						if (!callback.isIdFound()) throw new MappingException("Cannot perform cascade save on child object without id set");
						ArrayList<?> fieldValueList = (ArrayList<?>) fieldValue;
						fieldValueList.stream().forEach(value -> {
							mongoOperations.save(value);
						});
					}
					
					else {
						ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
						if (!callback.isIdFound()) throw new MappingException("Cannot perform cascade save on child object without id set");
						mongoOperations.save(fieldValue);
					}

				}
				
			}

		});

	}

	private static class DbRefFieldCallback implements ReflectionUtils.FieldCallback {

		private boolean idFound;

		public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
			ReflectionUtils.makeAccessible(field);
			if (field.isAnnotationPresent(Id.class)) {
				idFound = true;
			}
		}

		public boolean isIdFound() {
			return idFound;
		}

	}

}
