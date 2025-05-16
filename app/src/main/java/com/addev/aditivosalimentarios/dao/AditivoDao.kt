package com.addev.aditivosalimentarios.dao

import androidx.room.Dao
import androidx.room.Query
import com.addev.aditivosalimentarios.model.AdditiveWithAltNames
import java.util.Locale

@Dao
interface AditivoDao {

    @Query("SELECT * FROM additives WHERE UPPER(code) = UPPER(:term)")
    fun findByCode(term: String): List<AdditiveWithAltNames>

    @Query("""
        SELECT * FROM additives 
        WHERE LOWER(name) GLOB '*' || :term || '*' 
        OR id IN (SELECT additive_id FROM alt_names WHERE LOWER(alt_name) GLOB '*' || :term || '*')
    """)
    fun findByName(term: String): List<AdditiveWithAltNames>

    // Función para buscar múltiples términos
    fun findByNumbersOrNames(terms: List<String>): List<AdditiveWithAltNames> {
        val results = mutableListOf<AdditiveWithAltNames>()
        for (term in terms) {
            val trimmedTerm = term.trim().replace("\\s+".toRegex(), " ").lowercase()
            val normalizedTerm = normalizeCode(trimmedTerm)
            if (Regex("^E\\d{3}[a-z]?(?:\\([ivx]+\\))?\$", RegexOption.IGNORE_CASE).matches(normalizedTerm)) {
                results.addAll(findByCode(normalizedTerm))
            } else if (Regex("^\\d{3}[a-z]?(?:\\([ivx]+\\))?\$", RegexOption.IGNORE_CASE).matches(normalizedTerm)) {
                results.addAll(findByCode("E$normalizedTerm"))
            } else {
                results.addAll(findByName(addTildeOptions(trimmedTerm)))
            }

        }
        return results.distinct()
    }

    fun normalizeCode(term: String): String {
        // Expresión para capturar E opcional, número, letra opcional, y romanos opcionales (con o sin paréntesis)
        val regex = Regex("^(e)?(\\d{3})([a-z])?(?:\\(?(iv|ix|v?i{0,3})\\)?)?$", RegexOption.IGNORE_CASE)
        val match = regex.matchEntire(term)
        return if (match != null) {
            val (e, digits, letter, roman) = match.destructured
            buildString {
                if (e.isNotEmpty()) append(e.uppercase())
                append(digits)
                if (letter.isNotEmpty()) append(letter.lowercase())
                if (roman.isNotEmpty()) append("(${roman.lowercase()})")
            }
        } else term
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
