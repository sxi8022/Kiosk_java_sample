package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DuplicationUtil
{
    /*
    * @param list 중복이 있는 list
    * @param key 중복여부를 판단하는 키값
    * @param <T> generic Type
    * @return list
    * */
    public <T> List<T> deduplication(ArrayList<T> list, Function<? super T,?> key){
        return list.stream().filter(deduplication(key)).collect(Collectors.toList());
    }

    public <T>Predicate<T> deduplication(Function<? super T,?> key){
        Set<Object> set = ConcurrentHashMap.newKeySet();
        return predicate ->set.add(key.apply(predicate));
    }
}
