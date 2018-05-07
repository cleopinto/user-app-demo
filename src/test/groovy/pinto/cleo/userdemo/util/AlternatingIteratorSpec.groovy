package pinto.cleo.userdemo.util

import spock.lang.Specification
/**
 * Created by cleo on 5/6/18.
 */
class AlternatingIteratorSpec extends Specification {

    def 'test alternating iterator - alternates'() {
        when:
        List<String> stringList1 = ["a", "b"]
        List<Integer> intList = [1,2,3]
        List<String> stringList2 = ["x", "y", "z"]
        AlternatingIterator<ListIterator> alternatingIterator = new AlternatingIterator<>(stringList1.iterator(),
        intList.iterator(), stringList2.iterator())
        List<String> finalList = new ArrayList<>()
        while(alternatingIterator.hasNext()){
            finalList.add(String.valueOf(alternatingIterator.next()))
        }
        then:
        assert finalList == ["a", "1", "x", "b", "2", "y", "3", "z"]
    }

    def 'test alternating iterator - has next - false'() {
        when:
        Iterator it = Collections.emptyIterator()
        AlternatingIterator<ListIterator> alternatingIterator = new AlternatingIterator<>(it)
        then:
        assert !alternatingIterator.hasNext()
    }

    def 'test alternating iterator - next - returns as expected'() {
        when:
        Iterator it = ["a"].iterator()
        AlternatingIterator<ListIterator> alternatingIterator = new AlternatingIterator<>(it)
        then:
        assert alternatingIterator.next() == "a"
    }

    def 'test alternating iterator - next - nosuchelement'() {
        when:
        Iterator it = Collections.emptyIterator()
        AlternatingIterator<ListIterator> alternatingIterator = new AlternatingIterator<>(it)
        alternatingIterator.next()
        then:
        thrown NoSuchElementException
    }

    def 'alternating iterator throws nosuchelement exception'(){
        when:
        List<String> stringList1 = ["a", "b"]
        List<Integer> intList = [1,2,3]
        List<String> stringList2 = ["x", "y", "z"]
        AlternatingIterator<ListIterator> alternatingIterator = new AlternatingIterator<>(stringList1.iterator(),
                intList.iterator(), stringList2.iterator())
        List<String> finalList = new ArrayList<>()
        while(1){
            finalList.add(String.valueOf(alternatingIterator.next()))
        }
        then:
        thrown NoSuchElementException
    }

    def 'test alternating iterator - throws illegalargument if null is passed to constructor'() {
        when:
        AlternatingIterator<ListIterator> alternatingIterator = new AlternatingIterator<>(null)
        then:
        thrown IllegalArgumentException
    }

    def 'test alternating iterator - throws illegalargument if no args are passed to constructor'() {
        when:
        AlternatingIterator<ListIterator> alternatingIterator = new AlternatingIterator<>()
        then:
        thrown IllegalArgumentException
    }

    def 'test alternating iterator - throws illegalargument if empty iterator array passed to constructor'() {
        when:
        Iterator [] iterators = []
        AlternatingIterator<ListIterator> alternatingIterator = new AlternatingIterator<>(iterators)
        then:
        thrown IllegalArgumentException
    }
}
