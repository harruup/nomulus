// Copyright 2020 The Nomulus Authors. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package google.registry.persistence;

import static com.google.common.base.Preconditions.checkState;

import google.registry.model.ImmutableObject;
import java.util.Optional;

/**
 * VKey is an abstraction that encapsulates the key concept.
 *
 * <p>A VKey instance must contain both the JPA primary key for the referenced entity class and the
 * objectify key for the object.
 */
public class VKey<T> extends ImmutableObject {

  // The primary key for the referenced entity.
  private final Object primaryKey;

  // The objectify key for the referenced entity.
  private final com.googlecode.objectify.Key<T> ofyKey;

  private final Class<? extends T> kind;

  private VKey(Class<? extends T> kind, com.googlecode.objectify.Key<T> ofyKey, Object primaryKey) {
    this.kind = kind;
    this.ofyKey = ofyKey;
    this.primaryKey = primaryKey;
  }

  public static <T> VKey<T> createSql(Class<? extends T> kind, Object primaryKey) {
    return new VKey(kind, null, primaryKey);
  }

  public static <T> VKey<T> createOfy(
      Class<? extends T> kind, com.googlecode.objectify.Key<T> ofyKey) {
    return new VKey(kind, ofyKey, null);
  }

  public static <T> VKey<T> create(
      Class<? extends T> kind, Object primaryKey, com.googlecode.objectify.Key ofyKey) {
    return new VKey(kind, ofyKey, primaryKey);
  }

  public Class<? extends T> getKind() {
    return this.kind;
  }

  /** Returns the SQL primary key. */
  public Object getSqlKey() {
    checkState(primaryKey != null, "Attempting obtain a null SQL key.");
    return this.primaryKey;
  }

  /** Returns the SQL primary key if it exists. */
  public Optional<Object> maybeGetSqlKey() {
    return Optional.ofNullable(this.primaryKey);
  }

  /** Returns the objectify key. */
  public com.googlecode.objectify.Key<T> getOfyKey() {
    checkState(ofyKey != null, "Attempting obtain a null Objectify key.");
    return this.ofyKey;
  }

  /** Returns the objectify key if it exists. */
  public Optional<com.googlecode.objectify.Key<T>> maybeGetOfyKey() {
    return Optional.ofNullable(this.ofyKey);
  }
}
