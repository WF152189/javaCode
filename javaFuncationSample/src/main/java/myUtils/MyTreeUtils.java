package myUtils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.function.*;


public class MyTreeUtils {
    public static void main(String[] args) {
//        TreeToListDeepTest();
//        ListToTreeTest();
//        test((num) -> num == 0, 9);
        ListToTreeTest1();
    }
    public static void test(Predicate<Integer> fun, Integer param){
        System.out.println(fun.test(param));
    }
    public static void TreeToListDeepTest() {
        List<TestTreeObj> list = TestTreeObj.initData1();
        List<TestTreeObj> tag = new ArrayList<>();
        myTreeToListDeep(list,tag,TestTreeObj::getTestTreeObj,(l) -> l != null,false);
        String jsonString = JSON.toJSONString(tag);
        System.out.println(jsonString);
    }
    public static void ListToTreeTest1(){
        List<TestTreeObj> list = TestTreeObj.initData();
        listToTree(list,TestTreeObj::setTestTreeObj,TestTreeObj::getId,TestTreeObj::getPid,(item)->item.getPid() == 0,(count,item)->{item.setDeep(count);});
        System.out.println(JSON.toJSONString(list));
    }
    public static void ListToTreeTest2(){
        List<TestTreeObj> list = TestTreeObj.initData();
        HashMap<Integer, TestTreeObj> map = new HashMap<>();
        List<TestTreeObj> list1 = buildTree(list,
                0,
                map,
                TestTreeObj::getId,
                TestTreeObj::getPid,
                TestTreeObj::getTestTreeObj,
                TestTreeObj::setTestTreeObj,
                (pid)-> pid == 0);

        System.out.println(JSON.toJSONString(list1));
    }

    /**
     *
     * @param dataList      源数据
     * @param index         查找的起始位置
     * @param dataMap       map容器 把list中的元素按id-》item的方式存放，方便获取
     * @param IdFn          获取元素id
     * @param pIdFn         获取元素的pid
     * @param getChildFn    获取元素的子数组
     * @param setChildFn    设置元素的子数组
     * @param pidCondition  判断是否为根元素的条件
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T,K> List<T> buildTree(List<T> dataList,
                                          Integer index,
                                          HashMap<K, T> dataMap ,
                                          Function<T, K> IdFn,
                                          Function<T, K> pIdFn,
                                          Function<T, List<T>> getChildFn,
                                          BiConsumer<T, List<T>> setChildFn,
                                          Predicate<K> pidCondition){
        List<T> resultList = new ArrayList<>(dataList.size());
        while(index < dataList.size()){
            T item = dataList.get(index);
            dataMap.put(IdFn.apply(item),item);
            K pid = pIdFn.apply(item);
            // 满足父节点的判断条件时，需要保存到resultList
            if(pidCondition.test(pid)){
                resultList.add(item);
            }else{
                // 不满足判断条件时，从map中查找自己的父节点
                T parentItem = dataMap.get(pid);
                // 如果父节点为NULL，说明还没有找到父节点，递归查找父节点
                if(Objects.isNull(parentItem)){
                    index += 1;
                    List<T> list = buildTree(dataList, index, dataMap, IdFn, pIdFn, getChildFn, setChildFn, pidCondition);
                    parentItem = dataMap.get(pid);
                    // 如果递归结束后父节点还是NULL，就自动转为父节点
                    if(Objects.isNull(parentItem)){
                        resultList.add(item);
                    }else{
                        List<T> childList = Optional.ofNullable(getChildFn.apply(parentItem)).orElse(new ArrayList<>());
                        childList.add(item);
                        setChildFn.accept(parentItem,childList);
                    }
                    // 递归后的返回值要加到resultList
                    if(list.size() != 0) {
                        resultList.addAll(list);
                    }
                }else{
                    List<T> childList = Optional.ofNullable(getChildFn.apply(parentItem)).orElse(new ArrayList<>());
                    childList.add(item);
                    setChildFn.accept(parentItem,childList);
                }
            }
            index += 1;
        }
        return resultList;
    }
    /**
     *
     * @param source             源数据
     * @param setChildListFun    获取子节点
     * @param idFun             获取节点Id
     * @param pidFun            获取父节点Id
     * @param getRootCondition  判断是否为父节点的条件
     * @param <F>               返回值类型
     * @param <T>
     * @return
     */
    public static <F,T> List<F> listToTree(List<F> source, BiConsumer<F,List<F>> setChildListFun, Function<F,T> idFun,
                                            Function<F,T> pidFun, Predicate<F> getRootCondition){
        return listToTree(source, setChildListFun, idFun, pidFun, getRootCondition, null);

    }
    /**
     *
     * @param source             源数据
     * @param setChildListFun    获取子节点
     * @param idFun             获取节点Id
     * @param pidFun            获取父节点Id
     * @param getRootCondition  判断是否为父节点的条件
     * @param listen            回调函数(可以对每个节点进行特殊化的处理)
     * @param <F>               返回值类型
     * @param <T>
     * @return
     */
    public static <F,T> List<F> listToTree(List<F> source, BiConsumer<F,List<F>> setChildListFun, Function<F,T> idFun,
                                           Function<F,T> pidFun, Predicate<F> getRootCondition,BiConsumer<Integer,F> listen){
        List<F> tree = new ArrayList<>(source.size());
        Map<T, List<F>> map = new HashMap<>();
        // 遍历源数据，找到根节点
        source.forEach((item) -> {
            // 根据getRootCondition回调判断是否是根节点
            if(getRootCondition.test(item)) {
                tree.add(item);
            }else{
                // 不满足父节点的所有节点以节点的pid为Key，自身为Value保存起来
                List<F> tempList = map.getOrDefault(pidFun.apply(item),new ArrayList<>());
                tempList.add(item);
                map.put(pidFun.apply(item), tempList);
            }
        });
        // 递归遍历转为树结构
        tree.forEach((item)->assembleTree(item,map,setChildListFun,idFun,listen,0));
        return tree;
    }

    public static <F,T> void assembleTree(F current, Map<T, List<F>> map, BiConsumer<F, List<F>> setChildListFun,
                                          Function<F,T> idFun, BiConsumer<Integer, F> listen, int idx){
        T id = idFun.apply(current);
        List<F> fs = map.get(id);
        setChildListFun.accept(current, fs);
        if (CollectionUtils.isNotEmpty(fs)) {
            fs.forEach((item)->assembleTree(item,map,setChildListFun,idFun,listen,idx + 1));
        }
        if(listen != null){
            listen.accept(idx,current);
        }

    }
    /**
     * 树转平铺
     * @param source                 原始数据
     * @param target                 目标容器
     * @param childListFun           设置子节点函数
     * @param addTargetCondition     加入目标容器的条件
     * @param <T>
     */
    public static  <T> void myTreeToListDeep(List<T> source,
                                 List<T> target,
                                 Function<T,List<T>> childListFun,
                                 Predicate<T> addTargetCondition,
                                 Boolean pre            ) {
        if(pre) {
            myLoopTree(source, childListFun, (l) -> {
                if(addTargetCondition.test(l)) {
                    target.add(l);
                }                                    },null );
        }else{
            myLoopTree(source, childListFun, null,(l) -> {
                if(addTargetCondition.test(l)) {
                    target.add(l);
                }                                    } );
        }



    }

    /**
     *
     * @param source           原始数据
     * @param childListFun     设置子节点函数
     * @param preFun           递归前置回调
     * @param postFun          递归后置回调
     * @param <T>
     */
    public static  <T> void myLoopTree(List<T> source, Function<T, List<T>> childListFun,
                               Consumer<T> preFun, Consumer<T> postFun) {
        if(CollectionUtils.isEmpty(source)) {
            return;
        }
        source.forEach((item) -> {
            Optional.ofNullable(preFun).ifPresent(s -> s.accept(item));
            myLoopTree(childListFun.apply(item),childListFun, preFun, postFun);
            Optional.ofNullable(postFun).ifPresent(s ->s.accept(item));
        });

    }
}
