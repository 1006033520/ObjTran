package fb.util.transformation.adapter.field;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;

import fb.util.transformation.ObjTran;
import fb.util.transformation.adapter.object.ObjectAdapter;

class ClassField extends ObjectAdapter.ObjectChildField {




    private ClassField(@NotNull ObjTran context, @Nullable String key, @Nullable Object value, @NotNull Class<?> type, @NotNull Field field, @Nullable HashMap<String, Class<?>> genericTypes) {
        super(context, key, value, type, field, genericTypes);
    }
}
