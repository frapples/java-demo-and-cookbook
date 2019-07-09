package io.github.frapples.javademoandcookbook.commonutils.utils.script;

import java.math.BigDecimal;
import java.util.Map;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/7/9
 */
public class ScriptUtils {

    private static ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("js");
    private static SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    public static Object evalJavascript(String js, Map<String, Object> binding) throws ScriptException {
        Bindings bind = jsEngine.createBindings();
        bind.putAll(binding);
        return jsEngine.eval(js, bind);
    }

    public static Object evalJavascript(CompiledScript compiledScript, Map<String, Object> binding) throws ScriptException {
        Bindings bind = jsEngine.createBindings();
        bind.putAll(binding);
        return compiledScript.eval(bind);
    }

    public static CompiledScript compileJavascript(String js) throws ScriptException {
        Compilable compilable = (Compilable)jsEngine;
        return compilable.compile(js);
    }

    public static <T> T evalSpel(String spel, Map<String, Object> binding, Class<T> tClass) {
        StandardEvaluationContext stdContext = new StandardEvaluationContext();
        stdContext.setVariables(binding);
        Expression exp = spelExpressionParser.parseExpression(spel);
        return exp.getValue(stdContext, tClass);
    }

}
