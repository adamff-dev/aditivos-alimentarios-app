package com.addev.aditivosalimentarios.dao

import androidx.room.Dao
import androidx.room.Query
import com.addev.aditivosalimentarios.model.AdditiveWithAltNames
import java.util.Locale

@Dao
interface AditivoDao {

    @Query("SELECT * FROM additives WHERE UPPER(code) = UPPER(:term)")
    fun findByCode(term: String): List<AdditiveWithAltNames>

    @Query(
        """
        SELECT * FROM additives 
        WHERE LOWER(name) GLOB '*' || :term || '*' 
        OR id IN (SELECT additive_id FROM alt_names WHERE LOWER(alt_name) GLOB '*' || :term || '*')
    """
    )
    fun findByName(term: String): List<AdditiveWithAltNames>

    // Función para buscar múltiples términos
    fun findByNumbersOrNames(terms: List<String>): List<AdditiveWithAltNames> {
        val results = mutableListOf<AdditiveWithAltNames>()
        for (term in terms) {
            val trimmedTerm = term.trim().replace("\\s+".toRegex(), " ").lowercase()
            if (Regex("^E\\d{3,4}.*\$", RegexOption.IGNORE_CASE).matches(trimmedTerm)) {
                results.addAll(findByCode(trimmedTerm))
            } else if (Regex("^\\d{3,4}.*$", RegexOption.IGNORE_CASE).matches(trimmedTerm)) {
                results.addAll(findByCode("E$trimmedTerm"))
            } else {
                results.addAll(findByName(addTildeOptions(trimmedTerm)))
            }

        }
        return results.distinct()
    }

    @Query("SELECT * FROM additives")
    fun getAllAditivos(): List<AdditiveWithAltNames>

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
}
