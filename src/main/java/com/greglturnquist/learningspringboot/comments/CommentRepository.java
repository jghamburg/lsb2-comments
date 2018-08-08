/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.greglturnquist.learningspringboot.comments;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author Greg Turnquist
 */
// tag::code[]
public interface CommentRepository
	extends ReactiveCrudRepository<Comment, String> {

	Flux<Comment> findByImageId(String imageId);

	Flux<Comment> saveAll(Flux<Comment> newComment);

	// Required to support save()
	Mono<Comment> findById(String id);

	Mono<Void> deleteAll();
}
// end::code[]
