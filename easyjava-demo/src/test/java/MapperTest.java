import com.easyjava.RunDemoApplication;
import com.easyjava.entity.po.Blog;
import com.easyjava.entity.query.BlogQuery;
import com.easyjava.mappers.BlogMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author gao98
 */
@SpringBootTest(classes = RunDemoApplication.class)
@RunWith(SpringRunner.class)
public class MapperTest {
    @Resource
    private BlogMapper<Blog, BlogQuery> blogMapper;

    @Test
    public void selectList() {
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setCreateTimeStart("2022-09-25 11:20:18");
        blogQuery.setCreateTimeEnd("2025-01-11 21:11:57");
        blogQuery.setTitleFuzzy("介");
        List<Blog> list = blogMapper.selectList(blogQuery);
        for (Blog blog : list) {
            System.out.println(blog);
        }
        Integer count = blogMapper.selectCount(blogQuery);
        System.out.println(count);
    }

    @Test
    public void insert() {
        Blog blog = new Blog();
        blog.setBlogId("12312344");
        blog.setTitle("test2");
        Integer insert = blogMapper.insert(blog);
        System.out.println(blog.getBlogId());
    }

    @Test
    public void insertOrUpdate() {
        Blog blog = new Blog();
        blog.setBlogId("12312344");
        blog.setTitle("test23333");
        Integer insert = blogMapper.insertOrUpdate(blog);
        System.out.println(blog.getBlogId());
    }

    @Test
    public void selectById() {
        BlogQuery query = new BlogQuery();
        query.setBlogId("12312344");
        System.out.println(blogMapper.selectByBlogId(query.getBlogId()));
    }

    @Test
    public void insertBatch() {
        List<Blog> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Blog blog = new Blog();
            blog.setBlogId("123123124" + i);
            blog.setTitle("test" + i + 1);
            blog.setContent("芜湖起飞！！！");
            list.add(blog);
        }

        blogMapper.insertBatch(list);
    }

    @Test
    public void insertBatchOrUpdate() {
        List<Blog> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Blog blog = new Blog();
            blog.setBlogId("123123124" + i);
            blog.setContent("芜湖起飞！！！");
            list.add(blog);
        }

        blogMapper.insertOrUpdateBatch(list);
    }

    @Test
    public void deleteById() {
        BlogQuery query = new BlogQuery();
        query.setBlogId("12312344");
        System.out.println(blogMapper.deleteByBlogId(query.getBlogId()));
    }

    @Test
    public void updateById() {
        Blog query = new Blog();
        query.setBlogId("cfolUxvHga");
        query.setTitle("动手学深度学习");
        System.out.println(blogMapper.updateByBlogId(query, query.getBlogId()));
    }
}
