import java.lang.StringBuilder

class Person(val age: Int,
             val name: String,
             var mom: Person?,
             var dad: Person?,
             var siblings: List<Person>?
){

    var relativesCount: Int = 0

    private fun updateRelativesCount(count: Int){
        this.relativesCount = count
        this.siblings?.let {
            it.forEach {
                it.relativesCount = count
            }
        }

        this.dad?.updateRelativesCount(count)
        this.mom?.updateRelativesCount(count)
    }

    fun countRelatives() : Int{
        var count = 0

        this.siblings?.let {
            count += it.size
        }

        this.dad?.let {
            count += 1 + it.countRelatives()
        }
        this.mom?.let {
            count += 1 + it.countRelatives()
        }

        updateRelativesCount(count)

        return count
    }

    override fun toString(): String {
        val objectString = StringBuilder()

        objectString.append("Person(name=${this.name}, age=${this.age}, ")

        this.dad?.let {
            objectString.append("dad=${it.toString()}, ")
        } ?: objectString.append("dad=null, ")
        this.mom?.let {
            objectString.append("mom=${it.toString()}, ")
        } ?: objectString.append("mom=null, ")

        this.siblings?.let {
            objectString.append("siblings=[")
            it.forEach {
                objectString.append("${it.name},")
            }
            objectString.append("], ")
        } ?: objectString.append("siblings=null, ")

        objectString.append("relativesCount=${this.relativesCount})")


        return objectString.toString()
    }

}

fun main() {
    val me = getMe()
    println(me.toString())
}



fun getMe(): Person{
    val me = Person(20, "Dima", null, null, null)

    val dad = Person(45, "Dad", null, null, null)
    val mom = Person(45, "Mom", null, null, null)
    val aunt = Person(40, "Dad's sister", null, null, null)
    val uncle = Person(40, "Dad's brother", null, null, null)
    val aunt1 = Person(40, "Mom's sister", null, null, null)

    val grandpa = Person(70, "Dad's father", null, null, null)
    val grandma = Person(70, "Dad's mother", null, null, null)
    val grandpa1 = Person(70, "Mom's father", null, null, null)
    val grandma1 = Person(70, "Mom's mother", null, null, null)

    val grandGrandMa = Person(95, "Mom's grandmother", null, null, null)
    val grandGrandUncle = Person(90, "Mom's grandmother's brother", null, null, null)

    me.dad = dad
    me.mom = mom

    dad.siblings = mutableListOf(aunt, uncle)
    aunt.siblings = mutableListOf(dad, uncle)
    uncle.siblings = mutableListOf(dad, aunt)
    mom.siblings = mutableListOf(aunt1)
    aunt1.siblings = mutableListOf(mom)

    dad.dad = grandpa
    dad.mom = grandma
    mom.dad = grandpa1
    mom.mom = grandma1
    aunt.dad = grandpa
    aunt.mom = grandma
    uncle.dad = grandpa
    uncle.mom = grandma
    aunt1.dad = grandpa1
    aunt1.mom = grandma1

    grandma1.mom = grandGrandMa

    grandGrandMa.siblings = mutableListOf(grandGrandUncle)
    grandGrandUncle.siblings = mutableListOf(grandGrandMa)

    me.countRelatives()

    return me
}