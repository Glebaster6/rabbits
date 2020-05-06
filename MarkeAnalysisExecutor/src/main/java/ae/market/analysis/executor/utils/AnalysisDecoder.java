package ae.market.analysis.executor.utils;


import java.util.HashMap;
import java.util.Map;

public final class AnalysisDecoder {
    private static Map<String, String> abcValues;
    private static Map<String, String> xyzValues;
    private static Map<String, Map<String, String>> abcXyzValues;

    static {
        abcValues = new HashMap<>();
        xyzValues = new HashMap<>();
        abcXyzValues = new HashMap<>();
        abcValues.put(null, "-");
        abcValues.put("a", "Максимально ценные товары, занимают 20% ассортимента продукции, и приносят 80% прибыли от продаж");
        abcValues.put("b", "Малоценные товары, занимают 30% ассортимента продукции, и обеспечивают 15% продаж");
        abcValues.put("c", "Не востребованные товары, занимают 50% ассортимента, и обеспечивают 5% прибылей от продаж");
        xyzValues.put(null, "-");
        xyzValues.put("x", "Товары с наиболее устойчивыми объемами продаж");
        xyzValues.put("y", "Товары с прогнозируемыми, но изменчивыми объемами продаж");
        xyzValues.put("z", "Товары, обладающие случайным спросом");
        Map<String, String> aXyz = new HashMap<>();
        aXyz.put("x", "");
        aXyz.put("y", "");
        aXyz.put("z", "");
        Map<String, String> bXyz = new HashMap<>();
        bXyz.put("x", "");
        bXyz.put("y", "");
        bXyz.put("z", "");
        Map<String, String> cXyz = new HashMap<>();
        cXyz.put("x", "");
        cXyz.put("y", "");
        cXyz.put("z", "");
        abcXyzValues.put("a", aXyz);
        abcXyzValues.put("b", bXyz);
        abcXyzValues.put("c", cXyz);
    }

    public static String getAbcValue(String key) {
        return abcValues.get(key);
    }

    public static String getXyzValue(String key) {
        return xyzValues.get(key);
    }

    public static String getAbcXyzResult(String abcRes, String xyzRes) {
        return abcXyzValues.get(abcRes).get(xyzRes);
    }
}
