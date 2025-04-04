import com.invest7.controller.user.CpfValidate;
import com.invest7.controller.DataValidate;
import com.invest7.model.produtos.Produto;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestSimulacao {
    /// ///////////PRODUTO - VALIDAÇÃO/////////////////////////////////

    @Test
    void testConstrutorEGetters() {
        Produto produto = new Produto("CDB", 2500.00);

        // Verifica se o nome foi atribuído corretamente
        assertEquals("CDB", produto.getNome());

        // Verifica se o valor investido foi atribuído corretamente
        assertEquals(2500.00, produto.getValorInvestido(), 0.001);
    }

    @Test
    void testExibir() {
        Produto produto = new Produto("FIIs", 1500.00);

        // Captura a saída do console para verificar o método exibir()
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        produto.exibir();

        String expectedOutput = "Produto: FIIs\r\nValor Investido: R$ 1500.0\r\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    /// ///////////DATA - VALIDAÇÃO/////////////////////////////////
    @Test
    void testValidarDataFormatoCorreto() {
        DataValidate dataValidate = new DataValidate();
        String data = "2023/10/05";

        // Testa uma data no formato correto
        String resultado = dataValidate.validarData(data);

        // Verifica se a data foi formatada corretamente
        assertEquals("2023/10/05", resultado);
    }

    @Test
    void testValidarDataFormatoIncorreto() {
        DataValidate dataValidate = new DataValidate();
        String data = "05-10-2023"; // Formato incorreto

        // Testa uma data no formato incorreto
        String resultado = dataValidate.validarData(data);

        // Verifica se o método retornou null devido ao formato incorreto
        assertNull(resultado);
    }

    @Test
    void testValidarDataNula() {
        DataValidate dataValidate = new DataValidate();
        String data = null;

        // Testa uma data nula
        String resultado = dataValidate.validarData(data);

        // Verifica se o método retornou null para uma entrada nula
        assertNull(resultado);
    }

    @Test
    void testValidarDataVazia() {
        DataValidate dataValidate = new DataValidate();
        String data = ""; // String vazia

        // Testa uma data vazia
        String resultado = dataValidate.validarData(data);

        // Verifica se o método retornou null para uma entrada vazia
        assertNull(resultado);

    }

    /// ///////////CPF - VALIDAÇÃO/////////////////////////////////
    @Test
    void testValidaCpf_ValidCpf() {
        String validCpf = "529.982.247-25";
        String result = CpfValidate.validaCpf(validCpf);
        assertEquals("52998224725", result, "CPF válido deve ser retornado sem formatação.");
    }

    @Test
    void testValidaCpf_InvalidCpf() {
        String invalidCpf = "123.456.789-00";
        String result = CpfValidate.validaCpf(invalidCpf);
        assertNull(result, "CPF inválido deve retornar null.");
    }

    @Test
    void testValidaCpf_NullInput() {
        String nullCpf = null;
        String result = CpfValidate.validaCpf(nullCpf);
        assertNull(result, "Entrada nula deve retornar null.");
    }

    @Test
    void testValidaCpf_AllSameDigits() {
        String allSameDigitsCpf = "111.111.111-11";
        String result = CpfValidate.validaCpf(allSameDigitsCpf);
        assertNull(result, "CPF com todos os dígitos iguais deve retornar null.");
    }

    @Test
    void testValidaCpf_InvalidLength() {
        String invalidLengthCpf = "123.456.789";
        String result = CpfValidate.validaCpf(invalidLengthCpf);
        assertNull(result, "CPF com comprimento inválido deve retornar null.");
    }

    @Test
    void testValidaCpf_NonNumericCharacters() {
        String nonNumericCpf = "abc.def.ghi-jk";
        String result = CpfValidate.validaCpf(nonNumericCpf);
        assertNull(result, "CPF com caracteres não numéricos deve retornar null.");
    }

    @Test
    void testValidaCpf_ValidCpfWithoutFormatting() {
        String validCpfWithoutFormatting = "52998224725";
        String result = CpfValidate.validaCpf(validCpfWithoutFormatting);
        assertEquals("52998224725", result, "CPF válido sem formatação deve ser retornado corretamente.");
    }
}

