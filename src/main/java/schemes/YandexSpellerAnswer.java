package schemes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class YandexSpellerAnswer {
    public Integer code;
    public Integer pos;
    public Integer row;
    public Integer col;
    public Integer len;
    public String word;
    public List<String> s = new ArrayList<>();

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("code", code).append("pos", pos)
                .append("row", row).append("col", col).append("len", len)
                .append("word", word).append("s", s).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(col).append(code).append(s).append(len).append(pos).append(row)
                .append(word).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof YandexSpellerAnswer)) {
            return false;
        }
        YandexSpellerAnswer rhs = ((YandexSpellerAnswer) other);
        return new EqualsBuilder().append(col, rhs.col).append(code, rhs.code).append(s, rhs.s).append(len, rhs.len)
                .append(pos, rhs.pos).append(row, rhs.row).append(word, rhs.word).isEquals();
    }

}
