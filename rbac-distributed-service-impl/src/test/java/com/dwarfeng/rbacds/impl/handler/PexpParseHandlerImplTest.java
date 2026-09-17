package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.rbacds.stack.handler.PexpParseHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static com.dwarfeng.rbacds.stack.handler.PexpParseHandler.Modifier.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:com/dwarfeng/rbacds/impl/spring/application-context*.xml")
public class PexpParseHandlerImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PexpParseHandlerImplTest.class);

    private static final String SCOPE_STRING_ID = "test_scope";
    private static final String ROLE_STRING_ID = "test_role";
    private static final String PEXP_STRING_ID = "test_pexp";

    @Autowired
    private PexpParseHandler pexpParseHandler;

    @Test
    public void testForV1() {
        runTests(buildV1TestDatas());
    }

    @Test
    public void testForV2() {
        runTests(buildV2TestDatas());
    }

    @SuppressWarnings({"ConstantValue", "DataFlowIssue"})
    private List<PexpParseHandlerImplTestData> buildV1TestDatas() {
        List<PexpParseHandlerImplTestData> datas = new ArrayList<>();
        // 准备变量。
        Pexp pexp;
        PexpParseHandler.ParseResult expectedResult;
        boolean expectedExceptionThrow;

        // 1#: 合法表达式，不包含版本前缀。
        pexp = createPexp("+foo@bar");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 2#: 合法表达式，不包含版本前缀。
        pexp = createPexp("-foo@bar");
        expectedResult = createExpectResult(new Object[]{REJECT, "foo", "bar"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 3#: 合法表达式，不包含版本前缀。
        pexp = createPexp("!foo@bar");
        expectedResult = createExpectResult(new Object[]{GLOBAL_REJECT, "foo", "bar"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 4#: 合法表达式，包含版本前缀。
        pexp = createPexp("v1;+foo@bar");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 5#: 合法表达式，包含版本前缀。
        pexp = createPexp("v1;-foo@bar");
        expectedResult = createExpectResult(new Object[]{REJECT, "foo", "bar"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 6#: 合法表达式，包含版本前缀。
        pexp = createPexp("v1;!foo@bar");
        expectedResult = createExpectResult(new Object[]{GLOBAL_REJECT, "foo", "bar"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 7#: 不合法表达式，不包含版本前缀，非法的修饰符。
        pexp = createPexp("$foo@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 8#: 不合法表达式，包含版本前缀，非法的修饰符。
        pexp = createPexp("v1;$foo@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 9#: 不合法表达式，不包含版本前缀，空的修饰符+过滤器类型。
        pexp = createPexp("@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 10#: 不合法表达式，包含版本前缀，空的修饰符+过滤器类型。
        pexp = createPexp("v1;@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 11#: 不合法表达式，不包含版本前缀，空的过滤器类型。
        pexp = createPexp("+@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 12#: 不合法表达式，不包含版本前缀，空的过滤器类型。
        pexp = createPexp("-@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 13#: 不合法表达式，不包含版本前缀，空的过滤器类型。
        pexp = createPexp("!@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 14#: 不合法表达式，包含版本前缀，空的过滤器类型。
        pexp = createPexp("v1;+@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 15#: 不合法表达式，包含版本前缀，空的过滤器类型。
        pexp = createPexp("v1;-@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 16#: 不合法表达式，包含版本前缀，空的过滤器类型。
        pexp = createPexp("v1;!@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 17#: 不合法表达式，不包含版本前缀，不包含修饰符。
        pexp = createPexp("foo@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 18#: 不合法表达式，包含版本前缀，不包含修饰符。
        pexp = createPexp("v1;foo@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));
        return datas;
    }

    @SuppressWarnings({"ConstantValue", "DataFlowIssue"})
    private List<PexpParseHandlerImplTestData> buildV2TestDatas() {
        List<PexpParseHandlerImplTestData> datas = new ArrayList<>();
        // 准备变量。
        Pexp pexp;
        PexpParseHandler.ParseResult expectedResult;
        boolean expectedExceptionThrow;

        // 1#: 合法表达式，不包含管道。
        pexp = createPexp("v2;+foo@bar");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 2#: 合法表达式，不包含管道。
        pexp = createPexp("v2;-foo@bar");
        expectedResult = createExpectResult(new Object[]{REJECT, "foo", "bar"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 3#: 合法表达式，不包含管道。
        pexp = createPexp("v2;!foo@bar");
        expectedResult = createExpectResult(new Object[]{GLOBAL_REJECT, "foo", "bar"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 4#: 合法表达式，包含管道。
        pexp = createPexp("v2;+foo@bar|baz@qux");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar", "baz", "qux"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 5#: 合法表达式，包含管道。
        pexp = createPexp("v2;+foo@bar|baz@qux");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar", "baz", "qux"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 6#: 合法表达式，包含管道。
        pexp = createPexp("v2;+foo@bar|baz@qux|quux@bax");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar", "baz", "qux", "quux", "bax"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 7#: 不合法表达式，空字符串。
        pexp = createPexp("v2;");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 8#: 不合法表达式，缺少修饰符。
        pexp = createPexp("v2;foo@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 9#: 不合法表达式，包含管道，缺少修饰符。
        pexp = createPexp("v2;foo@bar|baz@qux");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 10#: 不合法表达式，缺少过滤器类型。
        pexp = createPexp("v2;+@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 11#: 不合法表达式，包含管道，缺少过滤器类型。
        pexp = createPexp("v2;+foo@bar|@qux");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 12#: 不合法表达式，管道符后没有内容。
        pexp = createPexp("v2;+foo@bar|");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 13#: 不合法表达式，管道符前没有内容。
        pexp = createPexp("v2;|foo@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 14#: 合法表达式，包含管道符转义。
        pexp = createPexp("v2;+foo@bar\\|bar|baz@qux");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar|bar", "baz", "qux"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 15#: 合法表达式，包含转义符转义。
        pexp = createPexp("v2;+foo@bar\\\\|bar@|baz@qux");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar\\", "bar", "", "baz", "qux"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 16#: 不合法表达式，非法转义。
        pexp = createPexp("v2;+foo@bar\\");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 17#: 不合法表达式，非法转义。
        pexp = createPexp("v2;+foo@bar\\b");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 18#: 不合法表达式，包含管道，非法转义。
        pexp = createPexp("v2;+foo@bar|baz@qux\\");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 19#: 不合法表达式，包含管道，非法转义。
        pexp = createPexp("v2;+foo@bar|baz@qux\\q");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 20#: 不合法表达式，缺少匹配模式分隔符。
        pexp = createPexp("v2;+bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 21#: 不合法表达式，缺少匹配模式分隔符。
        pexp = createPexp("v2;+bar\\@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 22#: 不合法表达式，包含管道，缺少匹配模式分隔符。
        pexp = createPexp("v2;+foo@bar|baz");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 23#: 不合法表达式，包含管道，缺少匹配模式分隔符。
        pexp = createPexp("v2;+foo@bar|baz\\@baz");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 24#: 不合法表达式，修饰符提前结束。
        pexp = createPexp("v2;+");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 25#: 不合法表达式，修饰符提前结束。
        pexp = createPexp("v2;-");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 26#: 不合法表达式，修饰符提前结束。
        pexp = createPexp("v2;!");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 27#: 合法表达式，包含管道，转义符转义。
        pexp = createPexp("v2;-foo\\\\foo@bar|baz\\\\@baz");
        expectedResult = createExpectResult(new Object[]{REJECT, "foo\\foo", "bar", "baz\\", "baz"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 28#: 合法表达式，包含管道，管道符转义。
        pexp = createPexp("v2;-foo\\|foo@bar|baz\\|@baz");
        expectedResult = createExpectResult(new Object[]{REJECT, "foo|foo", "bar", "baz|", "baz"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 29#: 不合法表达式，转义符提前结束。
        pexp = createPexp("v2;-foo\\");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 30#: 不合法表达式，非法转义符。
        pexp = createPexp("v2;-foo\\foo@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 31#: 不合法表达式，包含管道，非法转义符。
        pexp = createPexp("v2;+foo@bar|baz\\baz@qux");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 32#: 不合法表达式，单独出现的管道符。
        pexp = createPexp("v2;+foo|foo@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 33#: 不合法表达式，包含管道，单独出现的管道符。
        pexp = createPexp("v2;+foo@bar|baz|baz@qux");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 34#: 不合法表达式，单独出现的匹配模式分隔符。
        pexp = createPexp("v2;+foo@bar@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 35#: 不合法表达式，包含管道，单独出现的匹配模式分隔符。
        pexp = createPexp("v2;+foo@bar|baz@qux@qux");
        expectedResult = null;
        expectedExceptionThrow = true;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 36#: 合法表达式，匹配模式分隔符转义。
        pexp = createPexp("v2;+foo@bar\\@bar");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar@bar"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 37#: 合法表达式，包含管道，匹配模式分隔符转义。
        pexp = createPexp("v2;+foo@bar|baz@qux\\@qux");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar", "baz", "qux@qux"});
        expectedExceptionThrow = false;
        datas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));
        return datas;
    }

    private Pexp createPexp(String content) {
        return new Pexp(new PexpKey(SCOPE_STRING_ID, ROLE_STRING_ID, PEXP_STRING_ID), content, "remark");
    }

    /**
     * 创建包含指定内容的解析结果。
     *
     * <p>
     * 参数 <code>resultData</code> 的内容应该是一个长度为 <code>2n + 1</code> 的数组，数组中的第 <code>0</code>
     * 个元素表示  <code>{@link PexpParseHandler.Modifier} modifier</code>。<br>
     * 剩余元素 <code>2</code> 个元素一：<br>
     * 第 <code>0</code> 个元素表示 <code>{@link String} filterType</code>。<br>
     * 第 <code>1</code> 个元素表示 <code>{@link String} filterPattern</code>。
     *
     * @param resultData 指定的内容。
     * @return 包含指定内容的解析结果。
     */
    private PexpParseHandler.ParseResult createExpectResult(Object[] resultData) {
        // 判断长度。
        if (resultData.length < 3 || (resultData.length - 1) % 2 != 0) {
            throw new IllegalArgumentException("resultData 的长度不合法");
        }
        // 获取第 0 个元素，转换为 Modifier。
        PexpParseHandler.Modifier modifier = (PexpParseHandler.Modifier) resultData[0];
        // 剩余元素，每 2 个元素一组，转换为 FilterType 和 FilterPattern，并构造 PipeUnit。
        List<PexpParseHandler.PipeUnit> pipeUnits = new ArrayList<>();
        for (int i = 1; i < resultData.length; i += 2) {
            String filterType = (String) resultData[i];
            String filterPattern = (String) resultData[i + 1];
            pipeUnits.add(new PexpParseHandler.PipeUnit(filterType, filterPattern));
        }
        // 返回结果。
        return new PexpParseHandler.ParseResult(modifier, pipeUnits);
    }

    private void runTests(List<PexpParseHandlerImplTestData> testDatas) {

        // 测试每一个测试数据。
        for (int i = 0; i < testDatas.size(); i++) {
            LOGGER.info("测试数据: {}/{}", i + 1, testDatas.size());

            // 获取测试数据。
            PexpParseHandlerImplTestData testData = testDatas.get(i);

            // 获取字段值。
            Pexp pexp = testData.pexp();
            PexpParseHandler.ParseResult expectedResult = testData.expectedResult();
            boolean expectedExceptionThrow = testData.expectedExceptionThrow();

            // 执行测试。
            PexpParseHandler.ParseResult actualResult;
            boolean actualExceptionThrow;
            try {
                actualResult = pexpParseHandler.parse(pexp);
                actualExceptionThrow = false;
            } catch (Exception e) {
                actualResult = null;
                actualExceptionThrow = true;
            }

            // 判断结果。
            assertEquals(expectedExceptionThrow, actualExceptionThrow);
            assertEquals(expectedResult, actualResult);
        }
    }
}
