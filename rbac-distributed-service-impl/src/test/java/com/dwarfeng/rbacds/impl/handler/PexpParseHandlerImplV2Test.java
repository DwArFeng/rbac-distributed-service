package com.dwarfeng.rbacds.impl.handler;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.handler.PexpParseHandler;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static com.dwarfeng.rbacds.stack.handler.PexpParseHandler.Modifier.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PexpParseHandlerImplV2Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(PexpParseHandlerImplV2Test.class);

    private static final LongIdKey PEXP_KEY = new LongIdKey(12450);
    private static final StringIdKey ROLE_KEY = new StringIdKey("test.role");

    @Autowired
    private PexpParseHandler pexpParseHandler;

    private final List<PexpParseHandlerImplTestData> testDatas = new ArrayList<>();

    // 为了保证代码的可读性，此处代码不做简化。
    @SuppressWarnings({"ConstantValue", "DataFlowIssue"})
    @Before
    public void setUp() {
        // 准备变量。
        Pexp pexp;
        PexpParseHandler.ParseResult expectedResult;
        boolean expectedExceptionThrow;

        // 1#: 合法表达式，不包含管道。
        pexp = createPexp("v2;+foo@bar");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar"});
        expectedExceptionThrow = false;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 2#: 合法表达式，不包含管道。
        pexp = createPexp("v2;-foo@bar");
        expectedResult = createExpectResult(new Object[]{REJECT, "foo", "bar"});
        expectedExceptionThrow = false;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 3#: 合法表达式，不包含管道。
        pexp = createPexp("v2;!foo@bar");
        expectedResult = createExpectResult(new Object[]{GLOBAL_REJECT, "foo", "bar"});
        expectedExceptionThrow = false;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 4#: 合法表达式，包含管道。
        pexp = createPexp("v2;+foo@bar|baz@qux");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar", "baz", "qux"});
        expectedExceptionThrow = false;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 5#: 合法表达式，包含管道。
        pexp = createPexp("v2;+foo@bar|baz@qux");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar", "baz", "qux"});
        expectedExceptionThrow = false;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 6#: 合法表达式，包含管道。
        pexp = createPexp("v2;+foo@bar|baz@qux|quux@bax");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar", "baz", "qux", "quux", "bax"});
        expectedExceptionThrow = false;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 7#: 不合法表达式，空字符串。
        pexp = createPexp("v2;");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 8#: 不合法表达式，缺少修饰符。
        pexp = createPexp("v2;foo@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 9#: 不合法表达式，包含管道，缺少修饰符。
        pexp = createPexp("v2;foo@bar|baz@qux");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 10#: 不合法表达式，缺少过滤器类型。
        pexp = createPexp("v2;+@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 11#: 不合法表达式，包含管道，缺少过滤器类型。
        pexp = createPexp("v2;+foo@bar|@qux");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 12#: 不合法表达式，管道符后没有内容。
        pexp = createPexp("v2;+foo@bar|");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 13#: 不合法表达式，管道符前没有内容。
        pexp = createPexp("v2;|foo@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 14#: 合法表达式，包含管道符转义。
        pexp = createPexp("v2;+foo@bar\\|bar|baz@qux");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar|bar", "baz", "qux"});
        expectedExceptionThrow = false;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 15#: 合法表达式，包含转义符转义。
        pexp = createPexp("v2;+foo@bar\\\\|bar@|baz@qux");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar\\", "bar", "", "baz", "qux"});
        expectedExceptionThrow = false;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 16#: 不合法表达式，非法转义。
        pexp = createPexp("v2;+foo@bar\\");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 17#: 不合法表达式，非法转义。
        pexp = createPexp("v2;+foo@bar\\b");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 18#: 不合法表达式，包含管道，非法转义。
        pexp = createPexp("v2;+foo@bar|baz@qux\\");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 19#: 不合法表达式，包含管道，非法转义。
        pexp = createPexp("v2;+foo@bar|baz@qux\\q");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 20#: 不合法表达式，缺少匹配模式分隔符。
        pexp = createPexp("v2;+bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 21#: 不合法表达式，缺少匹配模式分隔符。
        pexp = createPexp("v2;+bar\\@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 22#: 不合法表达式，包含管道，缺少匹配模式分隔符。
        pexp = createPexp("v2;+foo@bar|baz");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 23#: 不合法表达式，包含管道，缺少匹配模式分隔符。
        pexp = createPexp("v2;+foo@bar|baz\\@baz");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 24#: 不合法表达式，修饰符提前结束。
        pexp = createPexp("v2;+");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 25#: 不合法表达式，修饰符提前结束。
        pexp = createPexp("v2;-");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 26#: 不合法表达式，修饰符提前结束。
        pexp = createPexp("v2;!");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 27#: 合法表达式，包含管道，转义符转义。
        pexp = createPexp("v2;-foo\\\\foo@bar|baz\\\\@baz");
        expectedResult = createExpectResult(new Object[]{REJECT, "foo\\foo", "bar", "baz\\", "baz"});
        expectedExceptionThrow = false;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 28#: 合法表达式，包含管道，管道符转义。
        pexp = createPexp("v2;-foo\\|foo@bar|baz\\|@baz");
        expectedResult = createExpectResult(new Object[]{REJECT, "foo|foo", "bar", "baz|", "baz"});
        expectedExceptionThrow = false;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 29#: 不合法表达式，转义符提前结束。
        pexp = createPexp("v2;-foo\\");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 30#: 不合法表达式，非法转义符。
        pexp = createPexp("v2;-foo\\foo@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 31#: 不合法表达式，包含管道，非法转义符。
        pexp = createPexp("v2;+foo@bar|baz\\baz@qux");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 32#: 不合法表达式，单独出现的管道符。
        pexp = createPexp("v2;+foo|foo@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 33#: 不合法表达式，包含管道，单独出现的管道符。
        pexp = createPexp("v2;+foo@bar|baz|baz@qux");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 34#: 不合法表达式，单独出现的匹配模式分隔符。
        pexp = createPexp("v2;+foo@bar@bar");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 35#: 不合法表达式，包含管道，单独出现的匹配模式分隔符。
        pexp = createPexp("v2;+foo@bar|baz@qux@qux");
        expectedResult = null;
        expectedExceptionThrow = true;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 36#: 合法表达式，匹配模式分隔符转义。
        pexp = createPexp("v2;+foo@bar\\@bar");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar@bar"});
        expectedExceptionThrow = false;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));

        // 37#: 合法表达式，包含管道，匹配模式分隔符转义。
        pexp = createPexp("v2;+foo@bar|baz@qux\\@qux");
        expectedResult = createExpectResult(new Object[]{ACCEPT, "foo", "bar", "baz", "qux@qux"});
        expectedExceptionThrow = false;
        testDatas.add(new PexpParseHandlerImplTestData(pexp, expectedResult, expectedExceptionThrow));
    }

    /**
     * 创建包含指定内容的权限表达式。
     *
     * @param content 指定的内容。
     * @return 包含指定内容的权限表达式。
     */
    private Pexp createPexp(String content) {
        return new Pexp(PEXP_KEY, ROLE_KEY, content, "description", "remark");
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

    @After
    public void tearDown() {
        testDatas.clear();
    }

    @Test
    public void test() {
        // 测试每一个测试数据。
        for (int i = 0; i < testDatas.size(); i++) {
            LOGGER.info("测试数据: {}/{}", i + 1, testDatas.size());

            // 获取测试数据。
            PexpParseHandlerImplTestData testData = testDatas.get(i);

            // 获取字段值。
            Pexp pexp = testData.getPexp();
            PexpParseHandler.ParseResult expectedResult = testData.getExpectedResult();
            boolean expectedExceptionThrow = testData.isExpectedExceptionThrow();

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
