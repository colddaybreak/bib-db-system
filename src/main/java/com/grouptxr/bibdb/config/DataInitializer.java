package com.groupxxx.bibdb.config;

import com.groupxxx.bibdb.model.entity.Publication;
import com.groupxxx.bibdb.model.entity.Tag;
import com.groupxxx.bibdb.model.entity.User;
import com.groupxxx.bibdb.model.repository.PublicationRepository;
import com.groupxxx.bibdb.model.repository.TagRepository;
import com.groupxxx.bibdb.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;
    private final TagRepository tagRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner seedDemoData() {
        return args -> {
            if (userRepository.count() > 0) {
                return;
            }
            User demo =
                    User.builder()
                            .username("demo")
                            .password(passwordEncoder.encode("demo123"))
                            .email("demo@example.com")
                            .build();
            userRepository.save(demo);

            Tag ml = tagRepository.save(Tag.builder().name("machine-learning").build());
            Tag db = tagRepository.save(Tag.builder().name("database").build());

            Publication p1 =
                    Publication.builder()
                            .title("Attention Is All You Need")
                            .authors("Vaswani et al.")
                            .year(2017)
                            .abstractText("Transformer architecture for sequence modeling.")
                            .doi("10.5555/3295222.3295349")
                            .url("https://arxiv.org/abs/1706.03762")
                            .bibtexRaw("@article{attention2017,...}")
                            .build();
            p1.getTags().add(ml);
            ml.getPublications().add(p1);
            publicationRepository.save(p1);

            Publication p2 =
                    Publication.builder()
                            .title("Readings in Database Systems")
                            .authors("Stonebraker, Hellerstein")
                            .year(2005)
                            .abstractText("Classic anthology on database research.")
                            .doi("")
                            .url("http://www.redbook.io")
                            .bibtexRaw("")
                            .build();
            p2.getTags().add(db);
            db.getPublications().add(p2);
            publicationRepository.save(p2);
        };
    }
}
