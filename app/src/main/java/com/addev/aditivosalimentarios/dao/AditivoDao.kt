package com.addev.aditivosalimentarios.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.addev.aditivosalimentarios.model.Aditivo
import java.util.Locale

@Dao
interface AditivoDao {

    @Query("SELECT * FROM aditivos WHERE UPPER(numero) = UPPER(:term)")
    fun findByNumber(term: String): List<Aditivo>

    @Query("SELECT * FROM aditivos WHERE LOWER(nombre) GLOB '*' || :term || '*'")
    fun findByName(term: String): List<Aditivo>

    // Función para buscar múltiples términos
    fun findByNumbersOrNames(terms: List<String>): List<Aditivo> {
        val results = mutableListOf<Aditivo>()
        for (term in terms) {
            val trimmedTerm = term.trim()
            // Verificar si el término es un número de aditivo utilizando regex
            if (Regex("^E\\d+\\w+$", RegexOption.IGNORE_CASE).matches(trimmedTerm)) {
                results.addAll(findByNumber(trimmedTerm))
            } else if (Regex("^\\d+\\w+$").matches(trimmedTerm)) {
                results.addAll(findByNumber("E$trimmedTerm"))
            } else {
                results.addAll(findByName(addTildeOptions(trimmedTerm)))
            }
        }
        return results.distinct() // Eliminar duplicados
    }

    fun addTildeOptions(searchText: String): String {
        return searchText.lowercase(Locale.getDefault())
            .replace("[aáàäâã]".toRegex(), "\\[aáàäâã\\]")
            .replace("[eéèëê]".toRegex(), "\\[eéèëê\\]")
            .replace("[iíìî]".toRegex(), "\\[iíìî\\]")
            .replace("[oóòöôõ]".toRegex(), "\\[oóòöôõ\\]")
            .replace("[uúùüû]".toRegex(), "\\[uúùüû\\]")
            .replace("*", "[*]")
            .replace("?", "[?]")
    }

    @Query("SELECT * FROM aditivos")
    fun getAllAditivos(): List<Aditivo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(aditivos: List<Aditivo>)
}
