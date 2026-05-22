package com.nyatetduwit.domain.usecase.category;

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
public final class AddCategoryUseCase_Factory implements Factory<AddCategoryUseCase> {
  private final Provider<CategoryRepository> repositoryProvider;

  public AddCategoryUseCase_Factory(Provider<CategoryRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AddCategoryUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static AddCategoryUseCase_Factory create(Provider<CategoryRepository> repositoryProvider) {
    return new AddCategoryUseCase_Factory(repositoryProvider);
  }

  public static AddCategoryUseCase newInstance(CategoryRepository repository) {
    return new AddCategoryUseCase(repository);
  }
}
