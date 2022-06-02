package tk.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
/**
 * 统一tk.mybatis接口
 * @see  CommonsMapper
 **/
public interface CommonsMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
