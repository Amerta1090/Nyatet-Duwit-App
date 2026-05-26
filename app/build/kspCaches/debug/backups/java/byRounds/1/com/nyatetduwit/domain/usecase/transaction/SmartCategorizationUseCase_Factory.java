package com.nyatetduwit.domain.usecase.transaction;

import com.nyatetduwit.domain.repository.CategoryRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class SmartCategorizationUseCase_Factory implements Factory<SmartCategorizationUseCase> {
  private final Provider<CategoryRepository> categoryRepositoryProvider;

  public SmartCategorizationUseCase_Factory(
      Provider<CategoryRepository> categoryRepositoryProvider) {
    this.categoryRepositoryProvider = categoryRepositoryProvider;
  }

  @Override
  public SmartCategorizationUseCase get() {
    return newInstance(categoryRepositoryProvider.get());
  }

  public static SmartCategorizationUseCase_Factory create(
      Provider<CategoryRepository> categoryRepositoryProvider) {
    return new SmartCategorizationUseCase_Factory(categoryRepositoryProvider);
  }

  public static SmartCategorizationUseCase newInstance(CategoryRepository categoryRepository) {
    return new SmartCategorizationUseCase(categoryRepository);
  }
}
